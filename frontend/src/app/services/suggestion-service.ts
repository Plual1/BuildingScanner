import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { BoundingBox, DecisionStatus, SuggestionModel } from '../model/suggestion';

// mirror of SuggestionDto in backend web/Dtos.java
interface SuggestionDto {
  id: number;
  box: BoundingBox;
  confidence: number;
  status: DecisionStatus;
  decidedBy: string | null;
  decidedAt: string | null;
}

/** Talks to the backend. Holds no state; the page owns what is shown. */
@Injectable({ providedIn: 'root' })
export class SuggestionService {
  private readonly http = inject(HttpClient);

  async uploadImage(file: File): Promise<SuggestionModel[]> {
    const form = new FormData();
    form.append('file', file);
    const dtos = await firstValueFrom(this.http.post<SuggestionDto[]>('/api/images', form));
    return dtos.map(toSuggestionModel);
  }

  async decide(suggestionId: number, accepted: boolean): Promise<SuggestionModel> {
    const dto = await firstValueFrom(
      this.http.post<SuggestionDto>(`/api/suggestions/${suggestionId}/decision`, { accepted }),
    );
    return toSuggestionModel(dto);
  }
}

function toSuggestionModel(dto: SuggestionDto): SuggestionModel {
  return { id: dto.id, box: dto.box, confidence: dto.confidence, status: dto.status, decidedBy: dto.decidedBy };
}

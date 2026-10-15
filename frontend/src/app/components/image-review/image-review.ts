import { Component, input, output, signal } from '@angular/core';
import { PercentPipe } from '@angular/common';
import { SuggestionModel } from '../../model/suggestion';

export interface Decision {
  suggestionId: number;
  accepted: boolean;
}

@Component({
  selector: 'app-image-review',
  imports: [PercentPipe],
  templateUrl: './image-review.html',
  styleUrl: './image-review.css',
})
export class ImageReview {
  readonly imageUrl = input.required<string>();
  readonly suggestions = input.required<SuggestionModel[]>();
  readonly disabled = input(false);
  readonly decided = output<Decision>();

  protected readonly selectedId = signal<number | null>(null);
  protected readonly imageWidth = signal(0);
  protected readonly imageHeight = signal(0);

  protected onImageLoad(event: Event): void {
    const img = event.target as HTMLImageElement;
    this.imageWidth.set(img.naturalWidth);
    this.imageHeight.set(img.naturalHeight);
  }

  // color is derived from id
  protected colorFor(id: number): string {
    return 'hsl(' + ((id * 137) % 360) + ', 90%, 50%)';
  }

  protected select(s: SuggestionModel): void {
    if (this.selectedId() === s.id) {
      this.selectedId.set(null);
    } else {
      this.selectedId.set(s.id);
    }
  }

  protected decide(s: SuggestionModel, accepted: boolean): void {
    this.decided.emit({ suggestionId: s.id, accepted });
    this.selectedId.set(null);
  }
}

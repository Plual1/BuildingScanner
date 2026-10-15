import { Component, computed, inject, signal } from '@angular/core';
import { ImageUpload } from '../../components/image-upload/image-upload';
import { Decision, ImageReview } from '../../components/image-review/image-review';
import { SuggestionService } from '../../services/suggestion-service';
import { UIState, summarize } from '../../model/ui-state';

@Component({
  selector: 'app-review-page',
  imports: [ImageUpload, ImageReview],
  templateUrl: './review-page.html',
  styleUrl: './review-page.css',
})
export class ReviewPage {
  private readonly service = inject(SuggestionService);

  protected readonly state = signal<UIState>({ imageUrl: null, suggestions: [], request: { status: 'idle' } });
  protected readonly busy = computed(() => {
    const status = this.state().request.status;
    return status === 'uploading' || status === 'deciding';
  });
  protected readonly summary = computed(() => summarize(this.state().suggestions));

  protected async uploadImage(file: File): Promise<void> {
    this.state.update((s) => ({ ...s, request: { status: 'uploading' } }));
    try {
      const suggestions = await this.service.uploadImage(file);
      const oldUrl = this.state().imageUrl;
      if (oldUrl) {
        URL.revokeObjectURL(oldUrl);
      }
      // only one image is shown at a time, so the old boxes are dropped
      this.state.set({ imageUrl: URL.createObjectURL(file), suggestions, request: { status: 'idle' } });
    } catch (error) {
      console.error(error);
      this.state.update((s) => ({
        ...s,
        request: { status: 'failed', message: 'Upload fehlgeschlagen. Bitte erneut versuchen.' },
      }));
    }
  }

  protected async decide({ suggestionId, accepted }: Decision): Promise<void> {
    this.state.update((s) => ({ ...s, request: { status: 'deciding', suggestionId } }));
    try {
      const updated = await this.service.decide(suggestionId, accepted);
      this.state.update((s) => ({
        ...s,
        suggestions: s.suggestions.map((old) => (old.id === updated.id ? updated : old)),
        request: { status: 'idle' },
      }));
    } catch (error) {
      console.error(error);
      this.state.update((s) => ({
        ...s,
        request: { status: 'failed', message: 'Entscheidung konnte nicht gespeichert werden. Bitte erneut versuchen.' },
      }));
    }
  }
}

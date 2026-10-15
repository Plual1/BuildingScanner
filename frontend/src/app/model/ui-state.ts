import { SuggestionModel } from './suggestion';

export type RequestState =
  | { status: 'idle' }
  | { status: 'uploading' }
  | { status: 'deciding'; suggestionId: number }
  | { status: 'failed'; message: string };

export interface UIState {
  imageUrl: string | null;
  suggestions: SuggestionModel[];
  request: RequestState;
}

export interface DecisionSummary {
  open: number;
  accepted: number;
  rejected: number;
}

export function summarize(suggestions: readonly SuggestionModel[]): DecisionSummary {
  return {
    open: suggestions.filter((s) => s.status === 'OPEN').length,
    accepted: suggestions.filter((s) => s.status === 'ACCEPTED').length,
    rejected: suggestions.filter((s) => s.status === 'REJECTED').length,
  };
}

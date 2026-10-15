export type DecisionStatus = 'OPEN' | 'ACCEPTED' | 'REJECTED';

export interface BoundingBox {
  x: number;
  y: number;
  width: number;
  height: number;
}

export interface SuggestionModel {
  id: number;
  box: BoundingBox;
  confidence: number;
  status: DecisionStatus;
  decidedBy: string | null;
}

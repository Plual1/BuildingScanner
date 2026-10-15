import { describe, expect, it } from 'vitest';
import { DecisionStatus, SuggestionModel } from './suggestion';
import { summarize } from './ui-state';

function suggestion(id: number, status: DecisionStatus): SuggestionModel {
  return { id, status, box: { x: 0, y: 0, width: 1, height: 1 }, confidence: 0.5, decidedBy: null };
}

describe('ui state', () => {
  it('counts suggestions per decision status', () => {
    const list = [suggestion(1, 'OPEN'), suggestion(2, 'ACCEPTED'), suggestion(3, 'OPEN'), suggestion(4, 'REJECTED')];

    expect(summarize(list)).toEqual({ open: 2, accepted: 1, rejected: 1 });
  });
});

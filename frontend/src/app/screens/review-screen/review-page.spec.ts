import { TestBed } from '@angular/core/testing';
import { describe, expect, it } from 'vitest';
import { SuggestionService } from '../../services/suggestion-service';
import { ReviewPage } from './review-page';

describe('ReviewPage', () => {
  it('creates', () => {
    TestBed.configureTestingModule({ providers: [{ provide: SuggestionService, useValue: {} }] });
    const fixture = TestBed.createComponent(ReviewPage);
    fixture.detectChanges();

    expect(fixture.componentInstance).toBeTruthy();
  });
});

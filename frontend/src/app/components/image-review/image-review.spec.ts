import { TestBed } from '@angular/core/testing';
import { describe, expect, it } from 'vitest';
import { ImageReview } from './image-review';

describe('ImageReview', () => {
  it('creates', () => {
    const fixture = TestBed.createComponent(ImageReview);
    fixture.componentRef.setInput('imageUrl', 'blob:test');
    fixture.componentRef.setInput('suggestions', []);
    fixture.detectChanges();

    expect(fixture.componentInstance).toBeTruthy();
  });
});

import { TestBed } from '@angular/core/testing';
import { describe, expect, it } from 'vitest';
import { ImageUpload } from './image-upload';

describe('ImageUpload', () => {
  it('creates', () => {
    const fixture = TestBed.createComponent(ImageUpload);
    fixture.detectChanges();

    expect(fixture.componentInstance).toBeTruthy();
  });
});

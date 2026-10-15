import { Component, input, output } from '@angular/core';

@Component({
  selector: 'app-image-upload',
  templateUrl: './image-upload.html',
  styleUrl: './image-upload.css',
})
export class ImageUpload {
  readonly disabled = input(false);
  readonly fileSelected = output<File>();

  protected onChange(event: Event): void {
    const element = event.target as HTMLInputElement;
    const file = element.files?.[0];
    if (file) {
      this.fileSelected.emit(file);
      element.value = '';
    }
  }
}

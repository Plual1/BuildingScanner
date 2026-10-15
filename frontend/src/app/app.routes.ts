import { Routes } from '@angular/router';
import { ReviewPage } from './screens/review-screen/review-page';

export const routes: Routes = [
  { path: '', component: ReviewPage },
  { path: '**', redirectTo: '' },
];

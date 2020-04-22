import { Route } from '@angular/router';

import { LearningComponent } from './learning.component';

export const LEARNING_ROUTE: Route = {
  path: '',
  component: LearningComponent,
  data: {
    authorities: [],
    pageTitle: 'home.title'
  }
};

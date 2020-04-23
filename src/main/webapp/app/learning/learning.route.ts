import { Route } from '@angular/router';

import { LearningComponent } from './learning.component';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';

export const LEARNING_ROUTE: Route = {
  path: '',
  component: LearningComponent,
  resolve: {
    pagingParams: JhiResolvePagingParams
  },
  data: {
    authorities: ['ROLE_USER'],
    defaultSort: 'id,asc',
    pageTitle: 'home.title'
  },
  canActivate: [UserRouteAccessService]
};

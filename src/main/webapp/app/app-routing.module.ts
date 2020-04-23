import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { errorRoute } from './layouts/error/error.route';
import { navbarRoute } from './layouts/navbar/navbar.route';
import { DEBUG_INFO_ENABLED } from 'app/app.constants';
import { sidebarRoute } from './layouts/sidebar/sidebar.route';

const LAYOUT_ROUTES = [navbarRoute, ...errorRoute, sidebarRoute];

@NgModule({
  imports: [
    RouterModule.forRoot(
      [
        {
          path: 'admin',
          loadChildren: () => import('./admin/admin.module').then(m => m.ElearnAdminModule)
        },
        {
          path: 'account',
          loadChildren: () => import('./account/account.module').then(m => m.ElearnAccountModule)
        },
        {
          path: 'learning',
          loadChildren: () => import('./learning/learning.module').then(m => m.ElearnLearningModule)
        },
        ...LAYOUT_ROUTES
      ],
      { enableTracing: false }
    )
  ],
  exports: [RouterModule]
})
export class ElearnAppRoutingModule {}

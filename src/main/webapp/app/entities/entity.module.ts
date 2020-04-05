import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'word',
        loadChildren: () => import('./word/word.module').then(m => m.ElearnWordModule)
      },
      {
        path: 'session',
        loadChildren: () => import('./session/session.module').then(m => m.ElearnSessionModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class ElearnEntityModule {}

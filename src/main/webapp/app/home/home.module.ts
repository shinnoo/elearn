import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ElearnSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [ElearnSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent]
})
export class ElearnHomeModule {}

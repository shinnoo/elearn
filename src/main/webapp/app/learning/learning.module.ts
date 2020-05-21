import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ElearnSharedModule } from 'app/shared/shared.module';
import { LEARNING_ROUTE } from './learning.route';
import { LearningComponent } from './learning.component';

@NgModule({
  imports: [ElearnSharedModule, RouterModule.forChild([LEARNING_ROUTE])],
  declarations: [LearningComponent]
})
export class ElearnLearningModule {}

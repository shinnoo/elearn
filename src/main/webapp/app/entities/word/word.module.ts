import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ElearnSharedModule } from 'app/shared/shared.module';
import { WordComponent } from './word.component';
import { WordDetailComponent } from './word-detail.component';
import { WordUpdateComponent } from './word-update.component';
import { WordDeletePopupComponent, WordDeleteDialogComponent } from './word-delete-dialog.component';
import { wordRoute, wordPopupRoute } from './word.route';

const ENTITY_STATES = [...wordRoute, ...wordPopupRoute];

@NgModule({
  imports: [ElearnSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [WordComponent, WordDetailComponent, WordUpdateComponent, WordDeleteDialogComponent, WordDeletePopupComponent],
  entryComponents: [WordDeleteDialogComponent]
})
export class ElearnWordModule {}

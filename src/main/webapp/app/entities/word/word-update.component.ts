import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IWord, Word } from 'app/shared/model/word.model';
import { WordService } from './word.service';
import { ISession } from 'app/shared/model/session.model';
import { SessionService } from 'app/entities/session/session.service';

@Component({
  selector: 'jhi-word-update',
  templateUrl: './word-update.component.html'
})
export class WordUpdateComponent implements OnInit {
  isSaving: boolean;

  sessions: ISession[];

  editForm = this.fb.group({
    id: [],
    name: [],
    definition: [],
    pronounce: [],
    form: [],
    example: [],
    collocations: [],
    synonym: [],
    sessionId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected wordService: WordService,
    protected sessionService: SessionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ word }) => {
      this.updateForm(word);
    });
    this.sessionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISession[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISession[]>) => response.body)
      )
      .subscribe((res: ISession[]) => (this.sessions = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(word: IWord) {
    this.editForm.patchValue({
      id: word.id,
      name: word.name,
      definition: word.definition,
      pronounce: word.pronounce,
      form: word.form,
      example: word.example,
      collocations: word.collocations,
      synonym: word.synonym,
      sessionId: word.sessionId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const word = this.createFromForm();
    if (word.id !== undefined) {
      this.subscribeToSaveResponse(this.wordService.update(word));
    } else {
      this.subscribeToSaveResponse(this.wordService.create(word));
    }
  }

  private createFromForm(): IWord {
    return {
      ...new Word(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      definition: this.editForm.get(['definition']).value,
      pronounce: this.editForm.get(['pronounce']).value,
      form: this.editForm.get(['form']).value,
      example: this.editForm.get(['example']).value,
      collocations: this.editForm.get(['collocations']).value,
      synonym: this.editForm.get(['synonym']).value,
      sessionId: this.editForm.get(['sessionId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWord>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackSessionById(index: number, item: ISession) {
    return item.id;
  }
}

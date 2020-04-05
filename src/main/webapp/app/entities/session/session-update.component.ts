import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ISession, Session } from 'app/shared/model/session.model';
import { SessionService } from './session.service';

@Component({
  selector: 'jhi-session-update',
  templateUrl: './session-update.component.html'
})
export class SessionUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    sessionNumber: [],
    scores: [],
    wrongAnswer: []
  });

  constructor(protected sessionService: SessionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ session }) => {
      this.updateForm(session);
    });
  }

  updateForm(session: ISession) {
    this.editForm.patchValue({
      id: session.id,
      sessionNumber: session.sessionNumber,
      scores: session.scores,
      wrongAnswer: session.wrongAnswer
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const session = this.createFromForm();
    if (session.id !== undefined) {
      this.subscribeToSaveResponse(this.sessionService.update(session));
    } else {
      this.subscribeToSaveResponse(this.sessionService.create(session));
    }
  }

  private createFromForm(): ISession {
    return {
      ...new Session(),
      id: this.editForm.get(['id']).value,
      sessionNumber: this.editForm.get(['sessionNumber']).value,
      scores: this.editForm.get(['scores']).value,
      wrongAnswer: this.editForm.get(['wrongAnswer']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISession>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}

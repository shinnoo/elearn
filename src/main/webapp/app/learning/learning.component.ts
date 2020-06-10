import { Component, OnInit } from '@angular/core';
import { SessionService } from 'app/entities/session/session.service';
import { HttpErrorResponse, HttpResponse, HttpHeaders } from '@angular/common/http';
import { ISession } from 'app/shared/model/session.model';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core/language/language.helper';
import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ActivatedRoute } from '@angular/router';
import { AccountService } from 'app/core/auth/account.service';
import { WordService } from 'app/entities/word/word.service';
import { IWord } from 'app/shared/model/word.model';

@Component({
  selector: 'jhi-learning',
  templateUrl: './learning.component.html',
  styleUrls: ['./learning.component.scss']
})
export class LearningComponent implements OnInit {
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;
  links: any;
  parseLinks: any;
  totalItems: any;
  sessions: ISession[];
  currentAccount: any;
  routeData: any;
  words: IWord[];

  constructor(
    private eventManager: JhiEventManager,
    private accountService: AccountService,
    private jhiLanguageHelper: JhiLanguageHelper,
    private sessionService: SessionService,
    private wordService: WordService,
    private jhiAlertService: JhiAlertService,
    protected activatedRoute: ActivatedRoute
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams.page;
      this.reverse = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
    });
  }

  ngOnInit() {
    this.accountService
      .identity()
      .then(account => {
        this.currentAccount = account;
      })
      .then(res => {
        console.log(this.currentAccount);
        this.loadAllSession(this.currentAccount.login);
        this.getWords(0);
      });
  }

  getWords(sessionNumber: number) {
    this.wordService
      .query({
        page: sessionNumber - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IWord[]>) => this.paginateWords(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  loadAllSession(user: String) {
    this.sessionService
      .getSessions(user, {
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<ISession[]>) => this.paginateSessions(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateSessions(data: ISession[], headers: HttpHeaders) {
    // this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.sessions = data;
    console.log(this.sessions);
  }

  protected paginateWords(data: IWord[], headers: HttpHeaders) {
    // this.links = this.parseLinks.parse(headers.get('link'));
    // this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.words = data;
    console.log(this.words);
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}

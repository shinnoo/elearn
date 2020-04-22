import { Component, OnInit } from '@angular/core';
import { SessionService } from 'app/entities/session/session.service';
import { HttpErrorResponse, HttpResponse, HttpHeaders } from '@angular/common/http';
import { ISession } from 'app/shared/model/session.model';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core/language/language.helper';
import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ActivatedRoute } from '@angular/router';
import { AccountService } from 'app/core/auth/account.service';

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

  constructor(
    private eventManager: JhiEventManager,
    private accountService: AccountService,
    private jhiLanguageHelper: JhiLanguageHelper,
    private sessionService: SessionService,
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
    this.loadAllSession();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    console.log('learning');
  }

  loadAllSession() {
    this.sessionService
      .query({
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
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.sessions = data;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}

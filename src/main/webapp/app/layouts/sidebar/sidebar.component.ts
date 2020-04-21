import { Component, OnInit, OnDestroy } from '@angular/core';
import { JhiEventManager } from 'ng-jhipster';
import { Subscription } from 'rxjs';

@Component({
  selector: 'jhi-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit, OnDestroy {
  sidebarStatus = false;
  eventSubscriber: Subscription;

  constructor(protected eventManager: JhiEventManager) {}

  ngOnInit() {
    this.registerChangeInNavbar();
  }

  registerChangeInNavbar() {
    this.eventSubscriber = this.eventManager.subscribe('clickNavbar', response => {
      this.sidebarStatus = !this.sidebarStatus;
    });
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }
}

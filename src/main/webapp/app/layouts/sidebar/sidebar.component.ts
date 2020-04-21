import { Component, OnInit } from '@angular/core';
import { JhiEventManager } from 'ng-jhipster';

@Component({
  selector: 'jhi-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {
  sidebar_status = true;

  constructor(protected eventManager: JhiEventManager) {}

  ngOnInit() {}
}

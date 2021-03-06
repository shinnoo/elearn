import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ElearnTestModule } from '../../../test.module';
import { SessionDeleteDialogComponent } from 'app/entities/session/session-delete-dialog.component';
import { SessionService } from 'app/entities/session/session.service';

describe('Component Tests', () => {
  describe('Session Management Delete Component', () => {
    let comp: SessionDeleteDialogComponent;
    let fixture: ComponentFixture<SessionDeleteDialogComponent>;
    let service: SessionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElearnTestModule],
        declarations: [SessionDeleteDialogComponent]
      })
        .overrideTemplate(SessionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SessionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SessionService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});

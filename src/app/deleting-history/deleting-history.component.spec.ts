import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeletingHistoryComponent } from './deleting-history.component';

describe('DeletingHistoryComponent', () => {
  let component: DeletingHistoryComponent;
  let fixture: ComponentFixture<DeletingHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DeletingHistoryComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DeletingHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

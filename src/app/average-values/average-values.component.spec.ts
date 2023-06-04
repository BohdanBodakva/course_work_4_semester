import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AverageValuesComponent } from './average-values.component';

describe('AverageValuesComponent', () => {
  let component: AverageValuesComponent;
  let fixture: ComponentFixture<AverageValuesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AverageValuesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AverageValuesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

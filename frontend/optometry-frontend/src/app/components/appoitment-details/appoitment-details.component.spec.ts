import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppoitmentDetailsComponent } from './appoitment-details.component';

describe('AppoitmentDetailsComponent', () => {
  let component: AppoitmentDetailsComponent;
  let fixture: ComponentFixture<AppoitmentDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AppoitmentDetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AppoitmentDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

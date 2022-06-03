import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AppointmentsComponent } from './appointments/appointments.component';
import { AppointmentDetailsComponent } from './appointment-details/appointment-details.component';

const routes: Routes = [
  { path: '', component: AppointmentsComponent, pathMatch: 'full' },
  {
    path: 'id',
    component: AppointmentDetailsComponent,
  },
];

@NgModule({
  declarations: [AppointmentsComponent, AppointmentDetailsComponent],
  imports: [RouterModule.forChild(routes), CommonModule, NgbModule],
  exports: [AppointmentsComponent, AppointmentDetailsComponent],
})
export class AppointmentModule {}

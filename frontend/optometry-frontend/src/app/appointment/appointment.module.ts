import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AppointmentsComponent } from './appointments/appointments.component';
import { AppointmentDetailsComponent } from './appointment-details/appointment-details.component';
import { NewAppointmentComponent } from './new-appointment/new-appointment.component';

const routes: Routes = [
  { path: '', component: AppointmentsComponent, pathMatch: 'full' },
  {
    path: 'id',
    component: AppointmentDetailsComponent,
  },
  { path: 'new-appointment', component: NewAppointmentComponent },
];

@NgModule({
  declarations: [
    AppointmentsComponent,
    AppointmentDetailsComponent,
    NewAppointmentComponent,
  ],
  imports: [
    RouterModule.forChild(routes),
    CommonModule,
    NgbModule,
    FormsModule,
  ],
  exports: [AppointmentsComponent, AppointmentDetailsComponent],
})
export class AppointmentModule {}

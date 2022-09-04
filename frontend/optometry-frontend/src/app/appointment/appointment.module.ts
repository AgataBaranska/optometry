import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AppointmentsComponent } from './appointments/appointments.component';
import { NewAppointmentComponent } from './new-appointment/new-appointment.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatInputModule } from '@angular/material/input';
import { AppointmentCardComponent } from './appointment-card/appointment-card.component';
import { DiseaseResolver } from './services/disease-resolver';
import { PatientResolver } from './services/patient-resolver';
import { AppointmentReasonsResolver } from './services/appointment-reasons-resolver';
import { AvailableGeneralDiseases } from './services/available-general-diseases';
import { AvailableVisionConditionsResolver } from './services/available-vision-conditions-resolver';
const routes: Routes = [
  { path: '', component: AppointmentsComponent, pathMatch: 'full' },

  {
    path: ':id/card',
    component: AppointmentCardComponent,
    resolve: {
      availableEyeDiseases: DiseaseResolver,
      availableAppointmentReasons: AppointmentReasonsResolver,
      availableGeneralDiseases: AvailableGeneralDiseases,
      availableVsionConditions: AvailableVisionConditionsResolver,
      patient: PatientResolver,
    },
  },
  { path: 'new-appointment', component: NewAppointmentComponent },
];

@NgModule({
  declarations: [
    AppointmentsComponent,

    NewAppointmentComponent,
    AppointmentCardComponent,
  ],
  imports: [
    RouterModule.forChild(routes),
    CommonModule,
    NgbModule,
    FormsModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatInputModule,
    ReactiveFormsModule,
  ],
  exports: [AppointmentsComponent],
  providers: [
    DiseaseResolver,
    PatientResolver,
    AppointmentReasonsResolver,
    AvailableGeneralDiseases,
    AvailableVisionConditionsResolver,
  ],
})
export class AppointmentModule {}

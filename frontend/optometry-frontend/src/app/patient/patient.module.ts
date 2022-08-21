import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { PatientsComponent } from './patients/patients.component';
import { PatientDetailsComponent } from './patient-details/patient-details.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { PatientService } from './services/patient.service';
const routes: Routes = [
  { path: '', component: PatientsComponent, pathMatch: 'full' },
  {
    path: 'id',
    component: PatientDetailsComponent,
  },
];

@NgModule({
  declarations: [PatientDetailsComponent, PatientsComponent],
  imports: [RouterModule.forChild(routes), CommonModule, NgbModule],
})
export class PatientModule {}

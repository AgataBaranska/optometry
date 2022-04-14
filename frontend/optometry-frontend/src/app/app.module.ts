import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { PatientsListComponent } from './components/patients-list/patients-list.component';
import { HttpClientModule } from '@angular/common/http';
import { PatientService } from './services/patient.service';
import { RouterModule, Routes } from '@angular/router';
import { SearchComponent } from './components/search/search.component';
import { VerticalNavBarComponent } from './components/vertical-nav-bar/vertical-nav-bar.component';
import { HomeComponent } from './components/home/home.component';
import { AppointmentsComponent } from './components/appointments/appointments.component';
import { StatisticsComponent } from './components/statistics/statistics.component';
import { PatientDetailsComponent } from './components/patient-details/patient-details.component';

const routes: Routes = [
  { path: 'search/:keyword', component: PatientsListComponent },
  { path: 'patients/:id', component: PatientDetailsComponent },
  { path: 'home', component: HomeComponent },
  { path: 'patients', component: PatientsListComponent },
  { path: 'appointments', component: AppointmentsComponent },
  { path: 'statistics', component: StatisticsComponent },
  { path: '', redirectTo: '/patients', pathMatch: 'full' },
  { path: '**', redirectTo: '/patients', pathMatch: 'full' },
];

@NgModule({
  declarations: [
    AppComponent,
    PatientsListComponent,
    SearchComponent,
    VerticalNavBarComponent,
    AppointmentsComponent,
    PatientDetailsComponent,
  ],
  imports: [RouterModule.forRoot(routes), BrowserModule, HttpClientModule],
  providers: [PatientService],
  bootstrap: [AppComponent],
})
export class AppModule {}

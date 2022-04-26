import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { PatientsListComponent } from './components/patients-list/patients-list.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { PatientService } from './services/patient.service';
import { RouterModule, Routes } from '@angular/router';
import { SearchComponent } from './components/search/search.component';
import { VerticalNavBarComponent } from './components/vertical-nav-bar/vertical-nav-bar.component';
import { HomeComponent } from './components/home/home.component';
import { AppointmentsComponent } from './components/appointments/appointments.component';
import { StatisticsComponent } from './components/statistics/statistics.component';
import { PatientDetailsComponent } from './components/patient-details/patient-details.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HttpIntercepterBasicAuthService } from './services/http-intercepter-basic-auth.service';
import { LoginComponent } from './components/login/login.component';
import { FormsModule } from '@angular/forms';
import { TopBarComponent } from './components/top-bar/top-bar.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
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
    LoginComponent,
    TopBarComponent,
  ],
  imports: [
    RouterModule.forRoot(routes),
    BrowserModule,
    HttpClientModule,
    NgbModule,
    FormsModule,
  ],
  providers: [
    PatientService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpIntercepterBasicAuthService,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}

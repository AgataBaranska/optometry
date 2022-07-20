import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { PatientService } from './services/patient.service';
import { RouterModule, Routes } from '@angular/router';
import { SearchComponent } from './components/search/search.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { LoginComponent } from './components/login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TopBarComponent } from './components/top-bar/top-bar.component';
import { RegisterComponent } from './components/register/register.component';
import { Interceptor } from './helpers/interceptor';
import { AuthenticationService } from './services/authentication.service';
import { HasRoleGuard } from './has-role.guard';
import { IsAuthenticatedGuard } from './is-authenticated.guard';
import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import { UserModule } from './user/user.module';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { AppointmentModule } from './appointment/appointment.module';
import { AppointmentsComponent } from './appointment/appointments/appointments.component';
import { PatientModule } from './patient/patient.module';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },

  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  // { path: 'search/:keyword', component: PatientsListComponent },

  {
    path: 'home',
    loadChildren: () => import('./home/home.module').then((m) => m.HomeModule),
    canActivate: [HasRoleGuard, IsAuthenticatedGuard],
    data: { role: ['ADMIN', 'PATIENT'] },
  },
  {
    path: 'users',
    loadChildren: () => import('./user/user.module').then((m) => m.UserModule),
    canActivate: [HasRoleGuard, IsAuthenticatedGuard],
    data: { role: ['ADMIN'] },
  },
  {
    path: 'appointments',
    loadChildren: () =>
      import('./appointment/appointment.module').then(
        (m) => m.AppointmentModule
      ),
    canActivate: [HasRoleGuard, IsAuthenticatedGuard],
    data: { role: ['ADMIN'] },
  },

  {
    path: 'patients',
    loadChildren: () =>
      import('./patient/patient.module').then((m) => m.PatientModule),
    canActivate: [HasRoleGuard, IsAuthenticatedGuard],
    data: { role: ['ADMIN'] },
  },
  {
    path: 'statistics',
    loadChildren: () =>
      import('./statistics/statistics.module').then((m) => m.StatisticsModule),
    canActivate: [HasRoleGuard, IsAuthenticatedGuard],
    data: { role: ['ADMIN'] },
  },
  { path: '**', component: PageNotFoundComponent, pathMatch: 'full' },
];

@NgModule({
  declarations: [
    AppComponent,
    SearchComponent,
    NavBarComponent,
    LoginComponent,
    TopBarComponent,
    RegisterComponent,
  ],
  imports: [
    RouterModule.forRoot(routes),
    BrowserModule,
    HttpClientModule,
    NgbModule,
    FormsModule,
    ReactiveFormsModule,
    UserModule,
    AppointmentModule,
    PatientModule,
  ],
  providers: [
    PatientService,
    AuthenticationService,
    [
      {
        provide: HTTP_INTERCEPTORS,
        useClass: Interceptor,
        multi: true,
      },
    ],
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}

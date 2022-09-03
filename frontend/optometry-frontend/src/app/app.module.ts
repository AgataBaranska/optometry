import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { RouterModule, Routes } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { LoginComponent } from './components/login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TopBarComponent } from './components/top-bar/top-bar.component';
import { RegisterComponent } from './components/register/register.component';
import { Interceptor } from './helpers/interceptor';
import { AuthenticationService } from './services/authentication.service';
import { HasRoleGuard } from './has-role.guard';
import { IsAuthenticatedGuard } from './is-authenticated.guard';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { RoleEnum } from './role-enum';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/home',
    pathMatch: 'full',
  },

  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  {
    path: 'home',
    loadChildren: () => import('./home/home.module').then((m) => m.HomeModule),
    canActivate: [HasRoleGuard, IsAuthenticatedGuard],
    data: {
      role: [
        RoleEnum.USER,
        RoleEnum.PATIENT,
        RoleEnum.OPTOMETRIST,
        RoleEnum.RECEPTIONIST,
        RoleEnum.ADMIN,
      ],
    },
  },
  {
    path: 'patients',
    loadChildren: () =>
      import('./patient/patient.module').then((m) => m.PatientModule),
    canActivate: [HasRoleGuard, IsAuthenticatedGuard],
    data: { role: [RoleEnum.RECEPTIONIST, RoleEnum.OPTOMETRIST] },
  },

  {
    path: 'users',
    loadChildren: () => import('./user/user.module').then((m) => m.UserModule),
    canActivate: [HasRoleGuard, IsAuthenticatedGuard],
    data: { role: [RoleEnum.ADMIN] },
  },
  {
    path: 'appointments',
    loadChildren: () =>
      import('./appointment/appointment.module').then(
        (m) => m.AppointmentModule
      ),
    canActivate: [HasRoleGuard, IsAuthenticatedGuard],
    data: {
      role: [
        RoleEnum.OPTOMETRIST,
        RoleEnum.ADMIN,
        RoleEnum.PATIENT,
        RoleEnum.RECEPTIONIST,
      ],
    },
  },
  {
    path: 'statistics',
    loadChildren: () =>
      import('./statistics/statistics.module').then((m) => m.StatisticsModule),
    canActivate: [HasRoleGuard, IsAuthenticatedGuard],
    data: { role: [RoleEnum.ADMIN] },
  },

  { path: '**', component: PageNotFoundComponent },
];

@NgModule({
  declarations: [
    AppComponent,
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
    BrowserAnimationsModule,
    NgbModule,
  ],
  providers: [
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

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UsersComponent } from './users/users.component';
import { RouterModule, Routes } from '@angular/router';
import { UserDetailsComponent } from './user-details/user-details.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { UserResolver } from './services/user.reslover';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRolesResolver } from './services/approles.resolver';

const routes: Routes = [
  { path: '', component: UsersComponent },
  {
    path: ':username',
    component: UserDetailsComponent,
    // before displaying UserDetailsComponent fetch user property
    resolve: {
      user: UserResolver,
      availableAppRoles: AppRolesResolver,
    },
  },
];

@NgModule({
  declarations: [UsersComponent, UserDetailsComponent],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    NgbModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  exports: [UsersComponent, UserDetailsComponent],
  providers: [UserResolver, AppRolesResolver],
})
export class UserModule {}

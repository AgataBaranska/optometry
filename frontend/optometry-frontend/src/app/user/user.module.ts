import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UsersComponent } from './users/users.component';
import { RouterModule, Routes } from '@angular/router';
import { UserDetailsComponent } from './user-details/user-details.component';

const routes: Routes = [
  { path: '', component: UsersComponent },
  // { path: 'id', component: UserDetailsComponent },
  // { path: 'roles', component: UserDetailsComponent },
];

@NgModule({
  declarations: [UsersComponent, UserDetailsComponent],
  imports: [CommonModule, RouterModule.forChild(routes)],
  exports: [UsersComponent],
})
export class UserModule {}

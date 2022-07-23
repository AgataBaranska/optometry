import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UsersComponent } from './users/users.component';
import { RouterModule, Routes } from '@angular/router';
import { UserDetailsComponent } from './user-details/user-details.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

const routes: Routes = [
  { path: '', component: UsersComponent },
  { path: ':username', component: UserDetailsComponent },
];

@NgModule({
  declarations: [UsersComponent, UserDetailsComponent],
  imports: [CommonModule, RouterModule.forChild(routes), NgbModule],
  exports: [UsersComponent, UserDetailsComponent],
})
export class UserModule {}

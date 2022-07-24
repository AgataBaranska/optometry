import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Role } from 'src/app/common/role';
import { User } from 'src/app/common/user';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css'],
})
export class UserDetailsComponent implements OnInit {
  user!: User;
  userDetailsFormGroup!: FormGroup;
  availableAppRoles: Array<Role> = [];

  constructor(
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private authService: AuthenticationService
  ) {}

  ngOnInit(): void {
    this.user = this.route.snapshot.data['user'];
    this.availableAppRoles = this.route.snapshot.data['availableAppRoles'];

    this.userDetailsFormGroup = this.formBuilder.group({
      user: this.formBuilder.group({
        username: new FormControl(this.user.username, [
          Validators.required,
          Validators.minLength(3),
          Validators.pattern('/^ +$/'),
        ]),

        password: [this.user.password],
        firstName: [this.user.firstName],
        lastName: [this.user.lastName],
        pesel: [this.user.pesel],
        email: [this.user.email],
        telephone: [this.user.telephone],
        roles: [this.availableAppRoles],
      }),
      address: this.formBuilder.group({
        street: [this.user.street],
        city: [this.user.city],
        country: [this.user.country],
        zipCode: [this.user.zipCode],
      }),
    });
  }

  hasUserRole(roleNameToCheck: string): boolean {
    for (var i = 0; i < this.user.roles.length; i++) {
      if (this.user.roles[i].name === roleNameToCheck) {
        return true;
      }
    }
    return false;
  }

  get username() {
    return this.userDetailsFormGroup.get('user.username');
  }

  updateUser() {
    if (confirm('Are you sure to update user ' + this.user.username + '?')) {
      console.log('Updating user ' + this.user.username);

      if (this.userDetailsFormGroup.invalid) {
        this.userDetailsFormGroup.markAllAsTouched();
        return;
      }
      let user = new User();
      Object.assign(user, this.userDetailsFormGroup.controls['user'].value);
      Object.assign(user, this.userDetailsFormGroup.controls['address'].value);

      this.authService.updateUser(user).subscribe({
        next: (response) => {
          alert('User updated succesfully');
        },
        error: (err) => {
          alert(`There was an error during user update: ${err.message}`);
        },
      });
    }
  }
  deleteUser(user: User) {
    if (confirm('Are you sure to delete ' + user.username + '?')) {
      console.log('Deleting user ' + user.username);
    }
  }
}

import { Component, OnInit } from '@angular/core';
import {
  FormArray,
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Role } from 'src/app/common/role';
import { User } from 'src/app/common/user';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { FormValidator } from 'src/app/validators/form-validator';
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
  selectedAppRoles: Array<Role> = [];

  constructor(
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.user = this.route.snapshot.data['user'];
    this.availableAppRoles = this.route.snapshot.data['availableAppRoles'];

    this.userDetailsFormGroup = this.formBuilder.group({
      user: this.formBuilder.group({
        username: new FormControl(this.user.username, [
          Validators.required,
          Validators.minLength(3),
          FormValidator.cannotContainSpace,
        ]),

        firstName: new FormControl(this.user.firstName, [Validators.required]),
        lastName: new FormControl(this.user.lastName, [Validators.required]),
        pesel: new FormControl(this.user.pesel, [
          Validators.minLength(11),
          Validators.maxLength(11),
          FormValidator.cannotContainSpace,
        ]),
        email: new FormControl(this.user.email, [
          Validators.required,
          Validators.email,
        ]),
        telephone: new FormControl(this.user.telephone, [
          Validators.required,
          Validators.pattern('^[0-9]*$'), //only digits
        ]),
        roles: this.addAvailableAppRoles(),
      }),
      address: this.formBuilder.group({
        street: new FormControl(this.user.street, [Validators.required]),
        city: new FormControl(this.user.city, [Validators.required]),
        country: new FormControl(this.user.country, [Validators.required]),
        postalCode: new FormControl(this.user.postalCode, [
          Validators.required,
        ]),
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
      user.roles = this.getSelectedAppRolesValue();

      this.userService.updateUser(user).subscribe({
        next: (response) => {
          alert('User updated succesfully');
        },
        error: (err) => {
          alert(`There was an error during user update: ${err.message}`);
        },
      });
    }
  }
  deleteUser() {
    let user: User = this.userDetailsFormGroup.controls['user'].value;

    if (confirm('Are you sure to delete ' + user.username + '?')) {
      this.userService.deleteUser(this.user).subscribe({
        next: (response) => {
          alert('User deleted succesfully');
        },
        error: (err) => {
          alert(`There was an error during user deletion: ${err.message}`);
        },
      });
    }
  }

  addAvailableAppRoles() {
    const arr = this.availableAppRoles.map((element) => {
      return this.formBuilder.control(this.hasUserRole(element.name));
    });
    return this.formBuilder.array(arr);
  }
  getSelectedAppRolesValue() {
    this.selectedAppRoles = [];
    this.availableAppRolesArray.controls.forEach((control, i) => {
      if (control.value) {
        this.selectedAppRoles.push(this.availableAppRoles[i]);
      }
    });
    return this.selectedAppRoles;
  }

  get availableAppRolesArray() {
    return <FormArray>this.userDetailsFormGroup.get('user.roles');
  }

  get username() {
    return this.userDetailsFormGroup.get('user.username');
  }
  get password() {
    return this.userDetailsFormGroup.get('user.password');
  }
  get firstName() {
    return this.userDetailsFormGroup.get('user.firstName');
  }
  get lastName() {
    return this.userDetailsFormGroup.get('user.lastName');
  }
  get pesel() {
    return this.userDetailsFormGroup.get('user.pesel');
  }
  get email() {
    return this.userDetailsFormGroup.get('user.email');
  }
  get telephone() {
    return this.userDetailsFormGroup.get('user.telephone');
  }
  get street() {
    return this.userDetailsFormGroup.get('user.street');
  }
  get city() {
    return this.userDetailsFormGroup.get('user.city');
  }
  get country() {
    return this.userDetailsFormGroup.get('user.country');
  }

  get postalCode() {
    return this.userDetailsFormGroup.get('user.postalCode');
  }
}

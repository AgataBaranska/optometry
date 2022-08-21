import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  FormControl,
} from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/common/user';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { FormValidator } from 'src/app/validators/form-validator';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  registerFormGroup!: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthenticationService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.registerFormGroup = this.formBuilder.group({
      user: this.formBuilder.group({
        username: new FormControl('', [
          Validators.required,
          Validators.minLength(3),
          FormValidator.cannotContainSpace,
        ]),

        password: new FormControl('', [
          Validators.required,
          Validators.minLength(5),
        ]),
        firstName: new FormControl('', [Validators.required]),
        lastName: new FormControl('', [Validators.required]),
        pesel: new FormControl('', [
          Validators.minLength(11),
          Validators.maxLength(11),
          FormValidator.cannotContainSpace,
        ]),
        email: new FormControl('', [Validators.required, Validators.email]),
        telephone: new FormControl('', [
          Validators.required,
          Validators.pattern('^[0-9]*$'),
        ]),
      }),
    });
  }

  register() {
    if (this.registerFormGroup.invalid) {
      this.registerFormGroup.markAllAsTouched();
      return;
    }

    let user = new User();
    Object.assign(user, this.registerFormGroup.controls['user'].value);
    Object.assign(user, this.registerFormGroup.controls['address'].value);
    this.authService.register(user).subscribe({
      next: (response) => {
        alert('New user registered succesfully');
        //TODO: does not reset form, does not navigate to login
        this.resetForm();
        this.router.navigateByUrl('/login');
      },
      error: (err) => {
        alert(`There was an error during registration: ${err.message}`);
      },
    });
  }

  resetForm() {
    this.registerFormGroup.reset();
  }

  get username() {
    return this.registerFormGroup.get('user.username');
  }
  get password() {
    return this.registerFormGroup.get('user.password');
  }
  get firstName() {
    return this.registerFormGroup.get('user.firstName');
  }
  get lastName() {
    return this.registerFormGroup.get('user.lastName');
  }
  get pesel() {
    return this.registerFormGroup.get('user.pesel');
  }
  get email() {
    return this.registerFormGroup.get('user.email');
  }
  get telephone() {
    return this.registerFormGroup.get('user.telephone');
  }
  get street() {
    return this.registerFormGroup.get('user.street');
  }
  get city() {
    return this.registerFormGroup.get('user.city');
  }
  get country() {
    return this.registerFormGroup.get('user.country');
  }

  get postalCode() {
    return this.registerFormGroup.get('user.postalCode');
  }
}

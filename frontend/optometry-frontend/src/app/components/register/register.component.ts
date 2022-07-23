import { Component, OnInit } from '@angular/core';
import {
  ReactiveFormsModule,
  FormBuilder,
  FormGroup,
  Validators,
  FormControl,
} from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/common/user';
import { AuthenticationService } from 'src/app/services/authentication.service';

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
          Validators.pattern('/^ +$/'),
        ]),

        password: [''],
        firstName: [''],
        lastName: [''],
        pesel: [''],
        email: [''],
        telephone: [''],
      }),
      address: this.formBuilder.group({
        street: [''],
        city: [''],
        country: [''],
        zipCode: [''],
      }),
    });
  }

  get username() {
    return this.registerFormGroup.get('user.username');
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
}

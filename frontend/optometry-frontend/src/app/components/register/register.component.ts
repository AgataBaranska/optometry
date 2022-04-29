import { Component, OnInit } from '@angular/core';
import {
  ReactiveFormsModule,
  FormBuilder,
  FormGroup,
  Validators,
  FormControl,
} from '@angular/forms';
import { Router } from '@angular/router';
import { Registration } from 'src/app/common/registration';
import { RegisterService } from 'src/app/services/register.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  registerFormGroup!: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private registerService: RegisterService,
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

    let registration = new Registration();
    registration.user = this.registerFormGroup.controls['user'].value;
    registration.address = this.registerFormGroup.controls['address'].value;
    this.registerService.register(registration).subscribe({
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

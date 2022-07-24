import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  loginFormGroup!: FormGroup;
  constructor(
    private router: Router,
    private authenticationService: AuthenticationService,
    private formBuilder: FormBuilder,
    private authService: AuthenticationService
  ) {}

  ngOnInit(): void {
    this.loginFormGroup = this.formBuilder.group({
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required]),
    });
  }

  login() {
    this.authenticationService
      .login(
        this.loginFormGroup.get('username')?.value,
        this.loginFormGroup.get('password')?.value
      )
      .subscribe({
        next: (response) => {
          this.router.navigateByUrl('/home');
        },
      });
  }

  get username() {
    return this.loginFormGroup.get('username');
  }
  get password() {
    return this.loginFormGroup.get('password');
  }
}

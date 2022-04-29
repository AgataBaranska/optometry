import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
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
    private formBuilder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.loginFormGroup = this.formBuilder.group({
      username: [''],
      password: [''],
    });
  }

  login() {
    this.authenticationService
      .login(
        this.loginFormGroup.get('username')?.value,
        this.loginFormGroup.get('password')?.value
      )
      .subscribe(
        (data) => {
          console.log('handle login data: ' + data);
          this.router.navigateByUrl('/patients');
        },
        (error) => {
          console.log(error);
        }
      );
  }
}

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { JWTUser } from 'src/app/common/JWTUser.model';
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
      username: [''],
      password: [''],
    });

    // if (this.authService.getToken()) {
    //   this.isLoggedIn = true;
    // }
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
}

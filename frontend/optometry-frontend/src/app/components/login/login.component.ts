import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  loginFormGroup!: FormGroup;
  isLoggedIn = false;

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService,
    private formBuilder: FormBuilder,
    private tokenStorage: TokenStorageService
  ) {}

  ngOnInit(): void {
    this.loginFormGroup = this.formBuilder.group({
      username: [''],
      password: [''],
    });

    // if (this.tokenStorage.getToken()) {
    //   this.isLoggedIn = true;
    // }
  }

  login() {
    this.authenticationService
      .login(
        this.loginFormGroup.get('username')?.value,
        this.loginFormGroup.get('password')?.value
      )
      .subscribe(
        (data) => {
          this.tokenStorage.saveToken(data.access_token);
          this.tokenStorage.saveRefreshToken(data.refresh_token);
          console.log(
            'access token from storage: ' + this.tokenStorage.getToken()
          );
          console.log(
            'refresh token from storage: ' + this.tokenStorage.getRefreshToken()
          );
          // this.isLoggedIn = true;
          //  window.location.reload();

          console.log('handle login data: ' + data);
          this.router.navigateByUrl('/patients');
        },
        (error) => {
          this.isLoggedIn = false;
          console.log(error);
        }
      );
  }
}

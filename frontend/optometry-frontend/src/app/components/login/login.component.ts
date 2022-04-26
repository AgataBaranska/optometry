import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BasicAuthenticationService } from 'src/app/services/basic-authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  username = 'admin';
  password = '';
  errorMessage = 'Invalid Login Credentials';
  invalidLogin = false;

  constructor(
    private basicAuthenticationService: BasicAuthenticationService,
    private router: Router
  ) {}

  ngOnInit(): void {}

  handleLogin() {
    this.basicAuthenticationService
      .executeAuthenticationService(this.username, this.password)
      .subscribe(
        (data) => {
          console.log('handle login data: ' + data);
          this.router.navigateByUrl('/patients');
          this.invalidLogin = false;
        },
        (error) => {
          console.log(error);
          this.invalidLogin = true;
        }
      );
  }
}

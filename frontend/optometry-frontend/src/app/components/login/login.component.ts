import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication.service';

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
    private router: Router,
    private authenticationService: AuthenticationService
  ) {}

  ngOnInit(): void {}

  login() {
    this.authenticationService.login(this.username, this.password).subscribe(
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

import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from './services/authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  title = 'optometry-frontend';

  constructor(
    private router: Router,
    public authService: AuthenticationService
  ) {}
  ngOnInit(): void {
    this.authService.isLoggedIn$.subscribe((isLoggedIn) => {
      if (isLoggedIn == false) {
        let route = '/login';
        this.router.navigate([route]);
      }
    });
  }
}

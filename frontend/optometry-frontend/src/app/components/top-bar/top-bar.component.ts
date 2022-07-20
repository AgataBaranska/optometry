import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrls: ['./top-bar.component.css'],
})
export class TopBarComponent implements OnInit {
  username: string | undefined;
  roles: string[];
  isAdmin: boolean;
  constructor(private authService: AuthenticationService) {
    this.username = authService.getUserName();
    this.roles = authService.getUserRoles();
    this.isAdmin = authService.hasRole('ADMIN');
  }

  ngOnInit(): void {}
  logout() {
    this.authService.logout();
  }
}

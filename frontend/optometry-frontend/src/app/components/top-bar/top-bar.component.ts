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
  currentRole: string;

  constructor(private authService: AuthenticationService) {
    this.username = authService.getUserName();
    this.roles = authService.getUserRoles();
    console.log('ROLES:', this.roles);

    //start with first role
    this.currentRole = authService.getUserRoles()[0];
  }

  ngOnInit(): void {
    this.authService.currentRole.subscribe((role) => (this.currentRole = role));
  }

  logout() {
    this.authService.logout();
  }

  switchRole(role: string) {
    console.log('switching role to: ' + role);
    this.authService.currentRole.next(role);
    this.ngOnInit();
  }
}

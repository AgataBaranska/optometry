import { Component, OnInit } from '@angular/core';
import { BasicAuthenticationService } from 'src/app/services/basic-authentication.service';

@Component({
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrls: ['./top-bar.component.css'],
})
export class TopBarComponent implements OnInit {
  constructor(private authenticationService: BasicAuthenticationService) {}

  ngOnInit(): void {}

  isUserLoggedIn() {
    return this.authenticationService.isUserLoggedIn();
  }
}

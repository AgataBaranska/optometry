import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  username: string | undefined;

  constructor(private authService: AuthenticationService) {
    this.username = authService.getUserName();
  }

  ngOnInit(): void {}
}

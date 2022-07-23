import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { User } from 'src/app/common/user';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css'],
})
export class UserDetailsComponent implements OnInit {
  user!: User;
  constructor(private userService: UserService, private route: ActivatedRoute) {
    console.log('User details constructor');
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => {
      this.handeUserDetails();
    });
  }
  handeUserDetails() {
    const userName: string = this.route.snapshot.paramMap.get('username')!;
    this.userService.getUserByUsername(userName).subscribe((data) => {
      this.user = data;
    });
  }
}

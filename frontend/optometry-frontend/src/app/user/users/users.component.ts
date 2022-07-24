import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/common/user';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css'],
})
export class UsersComponent implements OnInit {
  previousKeyword: string = '';
  users: User[] = [];
  showDialog = false;

  //pagination properties
  thePageSize: number = 2;
  thePageNumber: number = 1;
  theTotalElements: number = 0;

  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => this.listUsers());
  }
  listUsers(): void {
    const theKeyword: string = this.route.snapshot.paramMap.get('keyword')!;

    if (this.previousKeyword != theKeyword) {
      this.thePageNumber = 1;
    }
    this.previousKeyword = theKeyword;

    if (theKeyword) {
      //search using keyword
      this.userService
        .searchUsersPaginate(
          theKeyword,
          this.thePageNumber - 1,
          this.thePageSize
        )
        .subscribe(this.processResult());
    } else {
      this.userService
        .getUsersListPaginate(this.thePageNumber - 1, this.thePageSize)
        .subscribe(this.processResult());
    }
  }

  processResult() {
    return (data: any) => {
      this.users = data.content;
      this.thePageNumber = data.pageable.pageNumber + 1;
      this.thePageSize = data.pageable.pageSize;
      this.theTotalElements = data.totalElements;
    };
  }

  deleteUser(tempUser: User) {
    if (confirm('Are you sure to delete ' + tempUser.username + '?')) {
      console.log('Deleting user ' + tempUser.username);
    }
  }
}

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
  thePageSize: number = 4;
  thePageNumber: number = 1;
  theTotalElements: number = 0;

  constructor(
    private userService: UserService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => this.listUsers());
  }
  listUsers(): void {
    this.userService
      .getUsersListPaginate(this.thePageNumber - 1, this.thePageSize)
      .subscribe(this.processResult());
  }

  searchUsers(keyword: string) {
    this.userService
      .searchUsersPaginate(keyword, this.thePageNumber - 1, this.thePageSize)
      .subscribe(this.processResult());
  }

  processResult() {
    return (data: any) => {
      this.users = data.content;
      this.thePageNumber = data.pageable.pageNumber + 1;
      this.thePageSize = data.pageable.pageSize;
      this.theTotalElements = data.totalElements;
    };
  }
}

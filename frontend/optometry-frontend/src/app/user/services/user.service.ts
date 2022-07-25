import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_URL } from 'src/app/app.constants';
import { User } from 'src/app/common/user';
import { HttpClient } from '@angular/common/http';
import { Role } from 'src/app/common/role';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private baseUrl = `${API_URL}/api/users`;

  constructor(private httpClient: HttpClient) {}

  public getUsersListPaginate(
    thePage: number,
    thePageSize: number
  ): Observable<GetResponse> {
    const url = `${this.baseUrl}?page=${thePage}&size=${thePageSize}`;
    return this.httpClient.get<GetResponse>(url);
  }

  public getUserByUsername(username: string): Observable<User> {
    const userUrl = `${this.baseUrl}/${username}`;
    console.log('request path : ' + userUrl);
    return this.httpClient.get<User>(userUrl);
  }

  public searchUsersPaginate(
    lastName: string,
    thePage: number,
    thePageSize: number
  ): Observable<GetResponse> {
    const url = `${this.baseUrl}/search/findBylastNameContaining?lastName=${lastName}&page=${thePage}&size=${thePageSize}`;
    return this.httpClient.get<GetResponse>(url);
  }

  public getAllAppRoles(): Observable<Array<Role>> {
    const url = `${this.baseUrl}/roles`;
    return this.httpClient.get<Array<Role>>(url);
  }

  updateUser(user: User): Observable<any> {
    let url: string = `http://localhost:8080/api/users/${user.username}`;
    return this.httpClient.put<User>(url, user);
  }
}

interface GetResponse {
  content: User[];

  pageable: {
    pageSize: number;
    pageNumber: number;
  };
  totalElements: number;
  number: number;
}

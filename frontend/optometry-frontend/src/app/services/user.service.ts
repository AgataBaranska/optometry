import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_URL } from '../app.constants';
import { User } from '../common/user';

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

  getAppointment(theId: number): Observable<User> {
    const userUrl = `${this.baseUrl}/${theId}`;
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

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_URL } from '../app.constants';
const httpOptions = {
  headers: new HttpHeaders().set('Content-Type', 'application/json'),
};

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  constructor(private httpClient: HttpClient) {}

  login(username: string, password: string): Observable<any> {
    return this.httpClient.post(
      API_URL + '/api/login',
      { username: username, password: password },
      httpOptions
    );
  }
  refreshToken(token: string) {
    return this.httpClient.post(API_URL + '/token/refresh', {
      refreshToken: token,
      httpOptions,
    });
  }
}

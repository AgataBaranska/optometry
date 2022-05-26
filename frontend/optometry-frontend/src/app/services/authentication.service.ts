import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_URL } from '../app.constants';
import { Registration } from '../common/registration';
import { User } from '../common/user';
const TOKEN_KEY = 'access_token';
const REFRESHTOKEN_KEY = 'refresh-token';
const httpOptions = {
  headers: new HttpHeaders().set('Content-Type', 'application/json'),
};

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  user: User = new User();

  constructor(private httpClient: HttpClient) {}

  login(username: string, password: string): Observable<any> {
    return this.httpClient.post(
      API_URL + '/api/login',
      { username: username, password: password },
      httpOptions
    );
  }
  logout() {
    console.log('logging out');
    this.removeTokens();
  }
  removeTokens() {
    window.localStorage.clear();
  }

  register(registration: Registration): Observable<any> {
    let url: string = 'http://localhost:8080/api/users/registration';
    return this.httpClient.post<Registration>(url, registration);
  }

  public saveToken(token: string) {
    window.localStorage.removeItem(TOKEN_KEY);
    window.localStorage.setItem(TOKEN_KEY, token);
  }

  public getToken() {
    return window.localStorage.getItem(TOKEN_KEY);
  }

  public saveRefreshToken(token: string) {
    window.localStorage.removeItem(REFRESHTOKEN_KEY);
    window.localStorage.setItem(REFRESHTOKEN_KEY, token);
  }

  public getRefreshToken() {
    return window.localStorage.getItem(REFRESHTOKEN_KEY);
  }

  public refreshToken(token: string) {
    return this.httpClient.post(
      API_URL + '/api/token/refresh',
      {
        refresh_token: token,
      },
      httpOptions
    );
  }
}

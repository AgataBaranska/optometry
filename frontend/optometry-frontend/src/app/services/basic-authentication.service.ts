import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs';
import { API_URL } from '../app.constants';
export const TOKEN = 'token';
export const AUTHENTICATED_USER = 'authenticateUser';
@Injectable({
  providedIn: 'root',
})
export class BasicAuthenticationService {
  constructor(private httpClient: HttpClient) {}

  executeAuthenticationService(username: string, password: string) {
    let basicAuthHeaderString =
      'Basic ' + window.btoa(username + ':' + password);
    let headers = new HttpHeaders({
      Authorization: basicAuthHeaderString,
    });

    return this.httpClient
      .get<AuthenticationBean>(`${API_URL}/basicauth`, { headers })
      .pipe(
        map((data) => {
          sessionStorage.setItem(AUTHENTICATED_USER, username);
          sessionStorage.setItem(TOKEN, basicAuthHeaderString);
          return data;
        })
      );
  }

  getAthenticatedUser() {
    return sessionStorage.getItem(AUTHENTICATED_USER);
  }
  getAthenticatedToken() {
    if (this.getAthenticatedUser()) {
      return sessionStorage.getItem(TOKEN);
    }
    return null;
  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem(AUTHENTICATED_USER);
    return !(user === null);
  }

  logout() {
    sessionStorage.removeItem(AUTHENTICATED_USER);
  }
}
export class AuthenticationBean {
  constructor(public message: string) {}
}

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { API_URL } from '../app.constants';
import { JWTUser } from '../common/JWTUser.model';
import { Registration } from '../common/registration';
const TOKEN_KEY = 'access_token';
const REFRESHTOKEN_KEY = 'refresh-token';
const httpOptions = {
  headers: new HttpHeaders().set('Content-Type', 'application/json'),
};

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  jwtUser: JWTUser = {} as JWTUser;
  private _isLoggedIn$ = new BehaviorSubject<boolean>(false); //private to protect from external changes
  isLoggedIn$ = this._isLoggedIn$.asObservable();

  constructor(private httpClient: HttpClient) {
    if (this.getToken()) {
      //  this.jwtUser = this.setJWTUser(this.getToken());
    }

    //check if token expired before changing the state od isLoggedIn
    if (this.isTokenExpired(this.getToken())) {
      this._isLoggedIn$.next(false);
    }
    this._isLoggedIn$.next(!!this.getToken());
  }

  isTokenExpired(token: string) {
    if (token != null) {
      // const expiry = JSON.parse(atob(token.split('.')[1])).exp;
      // return Math.floor(new Date().getTime() / 1000) >= expiry;
    }
    return true;
  }

  login(username: string, password: string): Observable<any> {
    return this.httpClient
      .post(
        API_URL + '/api/login',
        { username: username, password: password },
        httpOptions
      )
      .pipe(
        tap((response: any) => {
          this.saveToken(response.access_token);
          this.saveRefreshToken(response.refresh_token);
          console.log('saving token ' + this.getToken());
          console.log('saving refresh token ' + this.getRefreshToken());
          this._isLoggedIn$.next(true);

          //save the user data from jwt
          // this.jwtUser = this.setJWTUser(response.access_token);
        })
      );
  }

  logout() {
    console.log('logging out');
    this.removeTokens();
    this._isLoggedIn$.next(false);
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

  public getToken(): any {
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

  // public setJWTUser(token: string): JWTUser {
  //   return JSON.parse(atob(token.split('.')[1])) as JWTUser;
  // }
}

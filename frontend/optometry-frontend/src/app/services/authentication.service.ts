import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import jwtDecode from 'jwt-decode';
import { BehaviorSubject, Observable, subscribeOn, tap } from 'rxjs';
import { API_URL } from '../app.constants';
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
  private _isLoggedIn$ = new BehaviorSubject<boolean>(false); //private to protect from external changes
  isLoggedIn$ = this._isLoggedIn$.asObservable();
  private currentRole$ = new BehaviorSubject<string>('');

  constructor(private httpClient: HttpClient) {
    //check if token expired before changing the state of isLoggedIn
    if (this.getToken() != null && !this.isTokenExpired(this.getToken()!)) {
      this._isLoggedIn$.next(true);
    }
  }
  get currentRole() {
    return this.currentRole$;
  }

  isTokenExpired(token: string) {
    if (token != null && token.length != 0) {
      let decodedToken = this.getTokenDecoded();
      if (decodedToken != null) {
        const expiry = decodedToken.exp;
        return Math.floor(new Date().getTime() / 1000) >= expiry;
      }
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

          //start with first user role
          this.currentRole$.next(this.getUserRoles()[0]);
        })
      );
  }

  logout() {
    console.log('logging out');
    this.removeTokens();
    this._isLoggedIn$.next(false);
  }
  removeTokens() {
    console.log('removing tokens');
    window.localStorage.clear();
  }

  register(user: User): Observable<any> {
    let url: string = 'http://localhost:8080/api/users';
    return this.httpClient.post<User>(url, user);
  }

  updateUser(user: User): Observable<any> {
    let url: string = `http://localhost:8080/api/users/${user.id}`;
    return this.httpClient.put<User>(url, user);
  }

  public saveToken(token: string) {
    window.localStorage.removeItem(TOKEN_KEY);
    window.localStorage.setItem(TOKEN_KEY, token);
  }

  public getTokenDecoded(): any {
    try {
      if (this.getToken() != null) {
        return jwtDecode(this.getToken()!);
      }
    } catch (Error) {
      return null;
    }
  }
  public getToken(): string | null {
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
      API_URL + '/api/users/token/refresh',
      {
        refresh_token: token,
      },
      httpOptions
    );
  }

  public getUserRoles(): string[] {
    if (this.getToken() != null) {
      let decodedToken = this.getTokenDecoded();

      if (decodedToken != null) {
        let roles = decodedToken.roles;
        return roles;
      }
    }
    return new Array();
  }

  public hasRole(role: string): boolean {
    return this.getUserRoles().indexOf(role) > -1;
  }

  public getUserName(): string {
    let decodedToken = this.getTokenDecoded();
    let username = decodedToken.sub;
    return username;
  }
}

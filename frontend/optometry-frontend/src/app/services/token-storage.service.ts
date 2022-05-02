import { Injectable } from '@angular/core';
const TOKEN_KEY = 'auth-token';
const REFRESHTOKEN_KEY = 'auth-refreshtoken';

@Injectable({
  providedIn: 'root',
})
export class TokenStorageService {
  signOut() {
    throw new Error('Method not implemented.');
  }
  constructor() {}

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
}

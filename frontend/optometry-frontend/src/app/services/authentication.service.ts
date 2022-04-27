import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_URL } from '../app.constants';
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'x-www-form-urlencoded' }),
};
@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  constructor(private httpClient: HttpClient) {}

  login(username: string, password: string): Observable<any> {
    let body = new URLSearchParams();
    body.set('username', username);
    body.set('password', password);
    return this.httpClient.post(
      API_URL + '/api/login',
      body.toString(),
      httpOptions
    );
  }
}

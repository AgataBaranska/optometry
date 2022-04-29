import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Registration } from '../common/registration';
import { User } from '../common/user';

@Injectable({
  providedIn: 'root',
})
export class RegisterService {
  user: User = new User();
  constructor(private httpClient: HttpClient) {}

  register(registration: Registration): Observable<any> {
    let url: string = 'http://localhost:8080/api/users/registration';
    return this.httpClient.post<Registration>(url, registration);
  }
}

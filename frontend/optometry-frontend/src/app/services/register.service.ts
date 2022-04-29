import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class RegisterService {
  constructor(private httpClient: HttpClient) {}

  register() {
    let url: string = 'http://localhost:8080/api/register';
  }
}

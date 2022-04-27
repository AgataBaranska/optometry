import {
  HttpEvent,
  HttpHandler,
  HttpHeaders,
  HttpInterceptor,
  HttpRequest,
  HTTP_INTERCEPTORS,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SessionStorageService } from '../services/session-storage.service';

const TOKEN_HEADER_KEY = 'Authorization';
@Injectable()
export class AuthorizationInterceptor implements HttpInterceptor {
  constructor(private sessionStorageService: SessionStorageService) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const token = this.sessionStorageService.getToken();
    let authorizationRequest = req;

    let header = new HttpHeaders({
      TOKEN_HEADER_KEY: 'Bearer ' + token,
      'Access-Control-Allow-Methods': 'POST, GET, OPTIONS',
      'Access-Control-Allow-Origin': 'http://localhost:4200',
      'Access-Control-Allow-Credentials': 'true',
    });
    if (token != null) {
      authorizationRequest = req.clone({
        headers: header,
      });
    }
    return next.handle(authorizationRequest);
  }
}

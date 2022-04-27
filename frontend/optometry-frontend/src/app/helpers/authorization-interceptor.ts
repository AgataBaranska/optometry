import {
  HttpEvent,
  HttpHandler,
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
    if (token != null) {
      authorizationRequest = req.clone({
        headers: req.headers.set(TOKEN_HEADER_KEY, 'Basic ' + token),
      });
    }
    return next.handle(authorizationRequest);
  }
}

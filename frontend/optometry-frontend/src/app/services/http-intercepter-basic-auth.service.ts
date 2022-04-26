import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BasicAuthenticationService } from './basic-authentication.service';

@Injectable({
  providedIn: 'root',
})
export class HttpIntercepterBasicAuthService implements HttpInterceptor {
  constructor(private authService: BasicAuthenticationService) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    let basicAuthHeaderString = this.authService.getAthenticatedToken();
    let username = this.authService.getAthenticatedUser();
    //if both exists add auth header
    if (username && basicAuthHeaderString) {
      request = request.clone({
        setHeaders: {
          Authorization: basicAuthHeaderString,
        },
      });
    }
    //forward with new header
    return next.handle(request);
  }
}

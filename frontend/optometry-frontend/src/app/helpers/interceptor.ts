import {
  HttpEvent,
  HttpHandler,
  HttpHeaders,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable } from 'rxjs';
import { TokenStorageService } from '../services/token-storage.service';

const TOKEN_HEADER_KEY = 'authorization';

@Injectable()
export class Interceptor implements HttpInterceptor {
  constructor(private tokenService: TokenStorageService) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const token = this.tokenService.getToken();
    let request = req;
    if (token) {
      request = this.addTokenHeader(req, token);
    }
    return next.handle(request);
  }

  private addTokenHeader(request: HttpRequest<any>, token: string) {
    console.log('Interceptor !! adding token to header');
    return request.clone({
      headers: request.headers.set(TOKEN_HEADER_KEY, 'Bearer ' + token),
    });
  }
}

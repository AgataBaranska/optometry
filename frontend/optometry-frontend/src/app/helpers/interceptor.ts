import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpHeaders,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import {
  BehaviorSubject,
  catchError,
  filter,
  Observable,
  switchMap,
  take,
  throwError,
} from 'rxjs';
import { AuthenticationService } from '../services/authentication.service';
import { TokenStorageService } from '../services/token-storage.service';

const TOKEN_HEADER_KEY = 'authorization';

@Injectable()
export class Interceptor implements HttpInterceptor {
  private isRefreshing = false; //flag
  private refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(
    null
  );

  constructor(
    private tokenService: TokenStorageService,
    private authService: AuthenticationService
  ) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const token = this.tokenService.getToken();
    let request = req;
    if (token) {
      request = this.addTokenHeader(req, token);
    }
    return next.handle(request).pipe(
      catchError((error) => {
        if (
          error instanceof HttpErrorResponse &&
          !request.url.includes('/login') &&
          error.status === 403
        ) {
          return this.handle403Error(request, next);
        }
        return throwError(error);
      })
    );
  }
  handle403Error(request: HttpRequest<any>, next: HttpHandler) {
    if (!this.isRefreshing) {
      //refreshing has not yet started
      this.isRefreshing = true; //refreshing started
      this.refreshTokenSubject.next(null); //populate next value of null, will block another request
      const refresh_token = this.tokenService.getRefreshToken();
      console.log('refresh token : ' + refresh_token);
      if (refresh_token) {
        return this.authService.refreshToken(refresh_token).pipe(
          switchMap((data: any) => {
            this.isRefreshing = false;
            console.log('New token: ' + data.access_token);
            this.tokenService.saveToken(data.access_token);

            this.refreshTokenSubject.next(data.access_token);
            //continue the request which initialized the method with new token
            return next.handle(
              this.addTokenHeader(request, this.tokenService.getToken()!)
            );
          }),

          catchError((err) => {
            this.isRefreshing = false;
            this.authService.logOut();
            return throwError(err);
          })
        );
      }
    }
    //block all the request until the value in refreshTokenSubject is not null
    return this.refreshTokenSubject.pipe(
      filter((token) => token !== null),
      //transform to observable which will finish after taking one event from subject
      take(1),
      //release that query
      switchMap((token) => next.handle(this.addTokenHeader(request, token)))
    );
  }

  private addTokenHeader(request: HttpRequest<any>, token: string) {
    console.log('Interceptor - adding token to header');
    if (request.url.includes('/refresh')) {
      return request;
    }
    return request.clone({
      headers: request.headers.set(TOKEN_HEADER_KEY, 'Bearer ' + token),
    });
  }
}

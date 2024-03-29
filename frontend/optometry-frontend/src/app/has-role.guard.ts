import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { Observable } from 'rxjs';
import { AuthenticationService } from './services/authentication.service';

@Injectable({
  providedIn: 'root',
})
export class HasRoleGuard implements CanActivate {
  constructor(private authenticationService: AuthenticationService) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    const userRoles = this.authenticationService.getUserRoles();

    const routeRoles = route.data['role'];

    let roleMatchesIndex = -1;
    roleMatchesIndex = userRoles?.findIndex(
      (role) => routeRoles.indexOf(role) != -1
    );

    let isAuthorized = roleMatchesIndex >= 0;

    if (!isAuthorized) {
      window.alert('You are not authorized to enter this path');
    }
    return isAuthorized;
  }
}

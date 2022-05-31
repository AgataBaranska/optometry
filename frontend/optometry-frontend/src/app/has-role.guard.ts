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
    const userRoles = this.authenticationService?.jwtUser?.roles;
    console.log(userRoles?.toString());

    const routeRoles = route.data['role'];
    console.log(routeRoles?.toString());

    const roleMatchesIndex = userRoles?.findIndex(
      (role) => routeRoles.indexOf(role) != -1
    );
    console.log(roleMatchesIndex.toString());
    let isAuthorized = roleMatchesIndex >= 0;

    if (!isAuthorized) {
      //redirect?

      window.alert('You are not authorized to enter this path');
    }
    return isAuthorized;
  }
}

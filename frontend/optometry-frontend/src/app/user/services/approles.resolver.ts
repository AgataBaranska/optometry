import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  Resolve,
  RouterStateSnapshot,
} from '@angular/router';
import { Observable } from 'rxjs';
import { Role } from 'src/app/common/role';
import { UserService } from './user.service';
@Injectable()
export class AppRolesResolver implements Resolve<Role[]> {
  constructor(private userService: UserService) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<Role[]> {
    return this.userService.getAllAppRoles();
  }
}

import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  Resolve,
  RouterStateSnapshot,
} from '@angular/router';
import { Observable } from 'rxjs';
import { AppointmentReason } from 'src/app/common/appointment-reason';
import { PatientService } from 'src/app/patient/services/patient.service';

@Injectable()
export class AppointmentReasonsResolver
  implements Resolve<AppointmentReason[]>
{
  constructor(private patientService: PatientService) {}
  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<AppointmentReason[]> {
    return this.patientService.getAvailableAppointmentReasons();
  }
}

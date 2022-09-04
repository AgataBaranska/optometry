import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  Resolve,
  RouterStateSnapshot,
} from '@angular/router';
import { Observable } from 'rxjs';
import { Disease } from 'src/app/common/disease';
import { PatientService } from 'src/app/patient/services/patient.service';

@Injectable()
export class AvailableGeneralDiseases implements Resolve<Disease[]> {
  constructor(private patientService: PatientService) {}
  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<Disease[]> {
    return this.patientService.getAvailableGeneralDiseases();
  }
}

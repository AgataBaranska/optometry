import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  Resolve,
  RouterStateSnapshot,
} from '@angular/router';
import { Observable } from 'rxjs';
import { Patient } from 'src/app/common/patient';
import { PatientService } from 'src/app/patient/services/patient.service';

@Injectable()
export class PatientResolver implements Resolve<Patient> {
  constructor(private patientService: PatientService) {}
  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<Patient> {
    const patientId: number = +route.paramMap.get('id')!;
    return this.patientService.getPatient(patientId);
  }
}

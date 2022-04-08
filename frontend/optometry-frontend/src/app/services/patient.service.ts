import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Patient } from '../common/patient';
import { map } from 'rxjs';
@Injectable({
  providedIn: 'root',
})
export class PatientService {
  private baseUrl = 'http://localhost:8080/api/patients';
  constructor(private httpClient: HttpClient) {}

  public getPatientList(): Observable<Patient[]> {
    return this.httpClient
      .get<GetResponse>(this.baseUrl)
      .pipe(map((response) => response._embeded.patients));
  }
}
interface GetResponse {
  _embeded: {
    patients: Patient[];
  };
}

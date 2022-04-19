import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Patient } from '../common/patient';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs';
@Injectable({
  providedIn: 'root',
})
export class PatientService {
  private baseUrl = 'http://localhost:8080/api/patients';

  constructor(private httpClient: HttpClient) {}

  public getPatientList(): Observable<Patient[]> {
    return this.getPatients(this.baseUrl);
  }
  public getPatientListPaginate(
    thePage: number,
    thePageSize: number
  ): Observable<GetResponse> {
    const url = `${this.baseUrl}?page=${thePage}&size=${thePageSize}`;
    return this.httpClient.get<GetResponse>(url);
  }

  private getPatients(url: string): Observable<Patient[]> {
    return this.httpClient
      .get<GetResponse>(url)
      .pipe(map((response) => response._embedded.patients));
  }

  getPatient(theId: number): Observable<Patient> {
    const patientUrl = `${this.baseUrl}/${theId}`;
    return this.httpClient.get<Patient>(patientUrl);
  }

  searchPatient(lastName: string): Observable<Patient[]> {
    const searchUrl: string = `${this.baseUrl}/search/findByLastNameContaining?lastName=${lastName}`;
    return this.getPatients(searchUrl);
  }

  public searchPatientPaginate(
    lastName: string,
    thePage: number,
    thePageSize: number
  ): Observable<GetResponse> {
    const url = `${this.baseUrl}/search/findByLastNameContaining?lastName=${lastName}&page=${thePage}&size=${thePageSize}`;
    return this.httpClient.get<GetResponse>(url);
  }
}
interface GetResponse {
  _embedded: {
    patients: Patient[];
  };
  page: {
    size: number;
    totalElements: number;
    totalPages: number;
    number: number;
  };
}

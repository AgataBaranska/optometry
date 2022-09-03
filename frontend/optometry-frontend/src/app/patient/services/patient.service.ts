import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Patient } from 'src/app/common/patient';
import { HttpClient } from '@angular/common/http';
import { API_URL } from 'src/app/app.constants';
import { map } from 'rxjs';
import { Disease } from 'src/app/common/disease';
import { AppointmentReason } from 'src/app/common/appointment-reason';
import { ContactLense } from 'src/app/common/contact-lense';

@Injectable({
  providedIn: 'root',
})
export class PatientService {
  private baseUrl = `${API_URL}/api/patients`;

  constructor(private httpClient: HttpClient) {}

  public getPatientList(): Observable<Patient[]> {
    return this.getPatients(this.baseUrl);
  }
  public getPatientListPaginate(
    thePage: number,
    thePageSize: number
  ): Observable<GetResponse> {
    const url = `${this.baseUrl}?page=${thePage}&size=${thePageSize}&sort=lastName`;
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

  public getAvailableEyeDiseases(): Observable<Disease[]> {
    const url = `${this.baseUrl}/availableDiseases?relatedOrgan=eye`;
    return this.httpClient.get<Disease[]>(url);
  }
  getAvailableGeneralDiseases() {
    const url = `${this.baseUrl}/availableDiseases?relatedOrgan=general`;
    return this.httpClient.get<Disease[]>(url);
  }

  getAvailableVisionConditions() {
    const url = `${this.baseUrl}/availableDiseases?relatedOrgan=visionCondition`;
    return this.httpClient.get<Disease[]>(url);
  }
  getAvailableAppointmentReasons() {
    const url = `${this.baseUrl}/availableAppointmentReasons`;
    return this.httpClient.get<AppointmentReason[]>(url);
  }

  getAvailableContactLenses() {
    const url = `${API_URL}/api/appointments/contactLenses`;
    return this.httpClient.get<ContactLense[]>(url);
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

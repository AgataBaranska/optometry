import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { API_URL } from 'src/app/app.constants';
import { Appointment } from 'src/app/common/appointment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AppointmentService {
  private baseUrl = `${API_URL}/api/appointments`;

  constructor(private httpClient: HttpClient) {}

  public getAppointmentListPaginate(
    thePage: number,
    thePageSize: number
  ): Observable<GetResponse> {
    const url = `${this.baseUrl}?page=${thePage}&size=${thePageSize}`;
    return this.httpClient.get<GetResponse>(url);
  }

  public searchAppointmentPaginate(
    work: string,
    thePage: number,
    thePageSize: number
  ): Observable<GetResponse> {
    const url = `${this.baseUrl}/search/findByWorkContaining?work=${work}&page=${thePage}&size=${thePageSize}`;
    return this.httpClient.get<GetResponse>(url);
  }

  getAppointment(theId: number): Observable<Appointment> {
    const appointmentUrl = `${this.baseUrl}/${theId}`;
    return this.httpClient.get<Appointment>(appointmentUrl);
  }
  /*


  

  searchPatient(lastName: string): Observable<Patient[]> {
    const searchUrl: string = `${this.baseUrl}/search/findByLastNameContaining?lastName=${lastName}`;
    return this.getPatients(searchUrl);
  }

 
}

  */
}

interface GetResponse {
  content: Appointment[];

  pageable: {
    pageSize: number;
    pageNumber: number;
  };
  totalElements: number;
  number: number;
}

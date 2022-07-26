import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { API_URL } from 'src/app/app.constants';
import { Appointment } from 'src/app/common/appointment';
import { Observable } from 'rxjs';
import { Optometrist } from 'src/app/common/optometrist';
import { Work } from 'src/app/common/work';

@Injectable({
  providedIn: 'root',
})
export class AppointmentService {
  private baseUrl = `${API_URL}/api/appointments`;

  constructor(private httpClient: HttpClient) {}

  public getOptometristList(): Observable<Optometrist[]> {
    const url = `${API_URL}/api/optometrists`;
    return this.httpClient.get<Optometrist[]>(url);
  }

  public getOptometristWorks(optometristId: number): Observable<Work[]> {
    const url = `${API_URL}/api/optometrists/${optometristId}/works`;
    return this.httpClient.get<Work[]>(url);
  }

  public getAppointmentListPaginate(
    thePage: number,
    thePageSize: number
  ): Observable<GetAppointmentsPaginateRespone> {
    const url = `${this.baseUrl}?page=${thePage}&size=${thePageSize}`;
    return this.httpClient.get<GetAppointmentsPaginateRespone>(url);
  }

  public searchAppointmentPaginate(
    work: string,
    thePage: number,
    thePageSize: number
  ): Observable<GetAppointmentsPaginateRespone> {
    const url = `${this.baseUrl}/search/findByWorkContaining?work=${work}&page=${thePage}&size=${thePageSize}`;
    return this.httpClient.get<GetAppointmentsPaginateRespone>(url);
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

interface GetAppointmentsPaginateRespone {
  content: Appointment[];

  pageable: {
    pageSize: number;
    pageNumber: number;
  };
  totalElements: number;
  number: number;
}

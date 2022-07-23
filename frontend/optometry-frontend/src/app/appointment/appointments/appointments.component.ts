import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Appointment } from 'src/app/common/appointment';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { AppointmentService } from '../services/appointment.service';

@Component({
  selector: 'app-appointments',
  templateUrl: './appointments.component.html',
  styleUrls: ['./appointments.component.css'],
})
export class AppointmentsComponent implements OnInit {
  appointments: Appointment[] = [];

  //pagination properties
  thePageSize: number = 2;
  thePageNumber: number = 1;
  theTotalElements: number = 0;

  previousKeyword: string = '';

  constructor(
    private appointmentService: AppointmentService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => this.listAppointments());
  }
  listAppointments(): void {
    const theKeyword: string = this.route.snapshot.paramMap.get('keyword')!;

    if (this.previousKeyword != theKeyword) {
      this.thePageNumber = 1;
    }
    this.previousKeyword = theKeyword;

    if (theKeyword) {
      //search using keyword
      this.appointmentService
        .searchAppointmentPaginate(
          theKeyword,
          this.thePageNumber - 1,
          this.thePageSize
        )
        .subscribe(this.processResult());
    } else {
      this.appointmentService
        .getAppointmentListPaginate(this.thePageNumber - 1, this.thePageSize)
        .subscribe(this.processResult());
    }
  }

  processResult() {
    return (data: any) => {
      this.appointments = data.content;
      this.thePageNumber = data.pageable.pageNumber + 1;
      this.thePageSize = data.pageable.pageSize;
      this.theTotalElements = data.totalElements;
    };
  }
}

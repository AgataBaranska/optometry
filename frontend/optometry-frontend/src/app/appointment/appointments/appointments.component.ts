import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
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
  currentRole!: string;
  currentUserName!: string;

  //pagination properties
  thePageSize: number = 2;
  thePageNumber: number = 1;
  theTotalElements: number = 0;

  constructor(
    private appointmentService: AppointmentService,
    private route: ActivatedRoute,
    private authService: AuthenticationService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.authService.currentRole.subscribe((role) => (this.currentRole = role));
    this.currentUserName = this.authService.getUserName();
    this.route.paramMap.subscribe(() => this.listOptometristAppointments());
  }
  listOptometristAppointments(): void {
    if (this.currentRole == 'PATIENT') {
      this.appointmentService
        .getAppointmentListForPatient(this.currentUserName)
        .subscribe((data: any) => (this.appointments = data));
    } else if (this.currentRole == 'OPTOMETRIST') {
      this.appointmentService
        .getAppointmentListForOptometrist(this.authService.getUserName())
        .subscribe((data: any) => (this.appointments = data));
    } else if (
      this.currentRole == 'RECEPTIONIST' ||
      this.currentRole == 'ADMIN'
    ) {
      this.appointmentService
        .getAppointmentList()
        .subscribe((data: any) => (this.appointments = data));
    }
  }

  onCancel(appointment: Appointment) {
    if (
      confirm(
        'Are you sure to cancel appointment for patient: ' +
          appointment.patientFirstName +
          ' ' +
          appointment.patientLastName +
          ', optometrist: ' +
          appointment.optometristFirstName +
          ' ' +
          appointment.optometristLastName +
          ', date: ' +
          appointment.date +
          ', time: ' +
          appointment.slot
      )
    ) {
      this.appointmentService.cancelAppointment(appointment.id).subscribe({
        next: (response) => {
          alert(`Appointment canceled!`);
          this.router.navigateByUrl('/appointments');
          this.ngOnInit();
        },
        error: (err) => {
          alert(`There was an error duringcancelation: ${err.message}`);
        },
      });
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

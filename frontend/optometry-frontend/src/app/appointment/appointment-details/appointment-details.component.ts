import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Appointment } from 'src/app/common/appointment';
import { AppointmentService } from '../services/appointment.service';

@Component({
  selector: 'app-appointment-details',
  templateUrl: './appointment-details.component.html',
  styleUrls: ['./appointment-details.component.css'],
})
export class AppointmentDetailsComponent implements OnInit {
  appointment!: Appointment;
  constructor(
    private appointmentService: AppointmentService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => {
      this.handleAppointmentDetails();
    });
  }

  handleAppointmentDetails() {
    const theId: number = +this.route.snapshot.paramMap.get('id')!;
    this.appointmentService.getAppointment(theId).subscribe((data) => {
      this.appointment = data;
    });
  }

  get duration() {
    let start = new Date(this.appointment.startTime);
    let end = new Date(this.appointment.endTime);
    let duration = Math.floor(
      (Date.UTC(end.getFullYear(), end.getMonth(), end.getDate()) -
        Date.UTC(start.getFullYear(), start.getMonth(), start.getDate())) /
        (1000 * 60)
    );
    console.log('Duration' + duration);
    return duration;
  }
}

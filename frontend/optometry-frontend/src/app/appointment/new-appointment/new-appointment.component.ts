import { Component, OnInit } from '@angular/core';
import { Optometrist } from 'src/app/common/optometrist';
import { Work } from 'src/app/common/work';
import { AppointmentService } from '../services/appointment.service';

@Component({
  selector: 'app-new-appointment',
  templateUrl: './new-appointment.component.html',
  styleUrls: ['./new-appointment.component.css'],
})
export class NewAppointmentComponent implements OnInit {
  optometrists: Optometrist[] = [];

  works: Work[] = [];

  constructor(private appointmentService: AppointmentService) {}

  ngOnInit(): void {
    this.showAll();
  }

  private showAll() {
    this.appointmentService.getOptometristList().subscribe((data) => {
      console.log(data);
      this.optometrists = data;
    });
  }

  onOptometristSelect(id: any) {
    this.appointmentService.getOptometristWorks(id).subscribe((data) => {
      this.works = data;
      console.log(this.works);
    });
  }

  onWorkSelect(id: any) {
    console.log('work selected');
  }
}

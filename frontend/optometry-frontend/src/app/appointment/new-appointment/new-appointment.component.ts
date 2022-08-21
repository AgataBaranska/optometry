import { Component, OnInit } from '@angular/core';
import { NewAppointmentDTO } from 'src/app/common/new-appointment-dto';
import { Optometrist } from 'src/app/common/optometrist';
import { Patient } from 'src/app/common/patient';
import { Work } from 'src/app/common/work';
import { PatientService } from 'src/app/patient/services/patient.service';
import { AppointmentService } from '../services/appointment.service';

@Component({
  selector: 'app-new-appointment',
  templateUrl: './new-appointment.component.html',
  styleUrls: ['./new-appointment.component.css'],
})
export class NewAppointmentComponent implements OnInit {
  optometrists: Optometrist[] = [];
  patients: Patient[] = [];

  works: Work[] = [];
  slots: Array<number>[] = [];
  selectedpatientId: number | undefined;
  selectedOptometristId: number | undefined;
  selectedWorkname: string | undefined;
  selectedDate: string | undefined;
  selectedSlot: number | undefined;

  constructor(
    private appointmentService: AppointmentService,
    private patientService: PatientService
  ) {}

  ngOnInit(): void {
    this.showAll();
  }

  private showAll() {
    this.appointmentService.getOptometristList().subscribe((data) => {
      console.log(data);
      this.optometrists = data;
    });
    this.patientService.getPatientList().subscribe((data) => {
      console.log(data);
      this.patients = data;
    });
  }
  onPatientSelect(id: any) {
    this.selectedpatientId = id;
  }
  onOptometristSelect(id: any) {
    this.selectedOptometristId = id;
    this.appointmentService.getOptometristWorks(id).subscribe((data) => {
      this.works = data;
    });
  }
  onWorkSelect(name: any) {
    this.selectedWorkname = name;
  }

  ondateSelect(event: any) {
    const stringified = JSON.stringify(event.value);
    const date = stringified.substring(1, 11);
    this.selectedDate = date;
    this.appointmentService
      .getAppointmentFreeTimeSlotsForDate(date, this.selectedOptometristId)
      .subscribe((data) => {
        this.slots = data;
      });
  }

  onSlotSelect(value: any) {
    this.selectedSlot = value;
  }

  scheduleAppointment() {
    var appointmentdto = new NewAppointmentDTO(
      this.selectedDate,
      this.selectedpatientId,
      this.selectedOptometristId,
      this.selectedWorkname,
      this.selectedSlot
    );
    this.appointmentService.saveNewAppointment(appointmentdto);
  }
}

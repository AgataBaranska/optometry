import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NewAppointmentDTO } from 'src/app/common/new-appointment-dto';
import { Optometrist } from 'src/app/common/optometrist';
import { Patient } from 'src/app/common/patient';
import { User } from 'src/app/common/user';
import { Work } from 'src/app/common/work';
import { PatientService } from 'src/app/patient/services/patient.service';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { AppointmentService } from '../services/appointment.service';

@Component({
  selector: 'app-new-appointment',
  templateUrl: './new-appointment.component.html',
  styleUrls: ['./new-appointment.component.css'],
})
export class NewAppointmentComponent implements OnInit {
  optometrists: Optometrist[] = [];
  patients: Patient[] = [];
  currentRole!: string;
  currentUser!: string;
  works: Work[] = [];
  slots: Array<number>[] = [];
  selectedpatientId: number | undefined;
  selectedOptometristId: number | undefined;
  selectedWorkname: string | undefined;
  selectedDate: string | undefined;
  selectedSlot: number | undefined;

  constructor(
    private appointmentService: AppointmentService,
    private patientService: PatientService,
    private authService: AuthenticationService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.showAll();
    this.authService.currentRole.subscribe((role) => (this.currentRole = role));
    this.currentUser = this.authService.getUserName();

    if (this.currentRole == 'PATIENT') {
      //initialize selectedPatientId
      this.patientService
        .getPatientByUsername(this.currentUser)
        .subscribe((data) => {
          this.selectedpatientId = data.id;
        });
    }
  }

  private showAll() {
    this.patientService.getPatientList().subscribe((data) => {
      console.log('patients', data);
      this.patients = data;
    });
    this.appointmentService.getOptometristList().subscribe((data) => {
      console.log('optometrists', data);
      this.optometrists = data;
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
    console.log('appointmentDTo', appointmentdto);
    this.appointmentService.saveNewAppointment(appointmentdto).subscribe({
      next: (response) => {
        alert(
          `New appointment scheduled succesfully for ${response.workName} at date: ${response.date}, time: ${response.slot}`
        );
        this.router.navigateByUrl('/appointments');
        this.ngOnInit();
      },
      error: (err) => {
        alert(`There was an error during registration: ${err.message}`);
      },
    });
  }
}

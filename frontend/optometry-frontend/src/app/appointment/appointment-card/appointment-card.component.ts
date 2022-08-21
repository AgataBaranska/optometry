import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Patient } from 'src/app/common/patient';
import { PatientService } from 'src/app/patient/services/patient.service';

@Component({
  selector: 'app-appointment-card',
  templateUrl: './appointment-card.component.html',
  styleUrls: ['./appointment-card.component.css'],
})
export class AppointmentCardComponent implements OnInit {
  appointmentCartFormGroup!: FormGroup;
  patient!: Patient;
  constructor(
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private patientService: PatientService
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => {
      this.handlePatientsDetails();
    });

    this.buildForm();
  }
  handlePatientsDetails() {
    const theId: number = +this.route.snapshot.paramMap.get('id')!;
    this.patientService.getPatient(theId).subscribe((data) => {
      this.patient = data;
    });
  }
  buildForm() {
    this.appointmentCartFormGroup = this.formBuilder.group({
      patient: this.formBuilder.group({}),
    });
  }

  saveAppointmentCard() {}
}

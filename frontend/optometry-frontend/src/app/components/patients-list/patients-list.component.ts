import { Component, OnInit } from '@angular/core';
import { Patient } from 'src/app/common/patient';
import { PatientService } from 'src/app/services/patient.service';

@Component({
  selector: 'app-patients-list',
  templateUrl: './patients-list.component.html',
  styleUrls: ['./patients-list.component.css'],
})
export class PatientsListComponent implements OnInit {
  public patients: Patient[] = [];
  constructor(private patientService: PatientService) {}

  ngOnInit(): void {
    this.listPatients();
  }

  listPatients() {
    this.patientService.getPatientList().subscribe((data) => {
      this.patients = data;
    });
  }
}

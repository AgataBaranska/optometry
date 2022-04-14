import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Patient } from 'src/app/common/patient';
import { PatientService } from 'src/app/services/patient.service';

@Component({
  selector: 'app-patients-list',
  templateUrl: './patients-list.component.html',
  styleUrls: ['./patients-list.component.css'],
})
export class PatientsListComponent implements OnInit {
  patients: Patient[] = [];
  searchMode: boolean = false;

  constructor(
    private patientService: PatientService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => this.listPatients());
  }

  listPatients() {
    const theKeyword: string = this.route.snapshot.paramMap.get('keyword')!;
    if (theKeyword) {
      //search using keyword
      this.patientService.searchPatient(theKeyword).subscribe((data) => {
        this.patients = data;
      });
    } else {
      this.patientService
        .getPatientList()
        .subscribe((data) => (this.patients = data));
    }
  }
}

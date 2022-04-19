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
  //pagination properties
  thePageSize: number = 2;
  thePageNumber: number = 1;
  theTotalElements: number = 0;
  previousKeyword: string = '';

  constructor(
    private patientService: PatientService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => this.listPatients());
  }

  listPatients() {
    const theKeyword: string = this.route.snapshot.paramMap.get('keyword')!;
    if (this.previousKeyword != theKeyword) {
      this.thePageNumber = 1;
    }
    this.previousKeyword = theKeyword;

    if (theKeyword) {
      //search using keyword
      this.patientService
        .searchPatientPaginate(
          theKeyword,
          this.thePageNumber - 1,
          this.thePageSize
        )
        .subscribe(this.processResult());
    } else {
      this.patientService
        .getPatientListPaginate(this.thePageNumber - 1, this.thePageSize)
        .subscribe(this.processResult());
    }
  }
  processResult() {
    return (data: any) => {
      this.patients = data._embedded.patients;
      this.thePageNumber = data.page.number + 1;
      this.thePageSize = data.page.size;
      this.theTotalElements = data.page.totalElements;
    };
  }
}

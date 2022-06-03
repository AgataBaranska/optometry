import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Patient } from 'src/app/common/patient';
import { PatientService } from 'src/app/services/patient.service';

@Component({
  selector: 'app-patient-details',
  templateUrl: './patient-details.component.html',
  styleUrls: ['./patient-details.component.css'],
})
export class PatientDetailsComponent implements OnInit {
  patient!: Patient;
  constructor(private route: ActivatedRoute, private service: PatientService) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => {
      this.handlePatientsDetails();
    });
  }
  handlePatientsDetails() {
    const theId: number = +this.route.snapshot.paramMap.get('id')!;
    this.service.getPatient(theId).subscribe((data) => {
      this.patient = data;
    });
  }
}

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { AppointmentReason } from 'src/app/common/appointment-reason';
import { ContactLense } from 'src/app/common/contact-lense';
import { Disease } from 'src/app/common/disease';
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
  availableEyeDiseases!: Disease[];
  availableGeneralDiseases!: Disease[];
  availableVsionConditions!: Disease[];
  availableAppointmentReasons!: AppointmentReason[];
  availableContactLenses!: ContactLense[];
  constructor(
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private patientService: PatientService
  ) {}

  ngOnInit(): void {
    this.patient = this.route.snapshot.data['patient'];
    this.availableEyeDiseases =
      this.route.snapshot.data['availableEyeDiseases'];

    this.handleAvailableGeneralDiseases();
    this.handleAvailableVisionConditions();
    this.handleAvailableAppointmentReasons();
    this.handleAvailableContactLenses();
    console.log(this.availableContactLenses);

    this.buildForm();
  }
  handleAvailableContactLenses() {
    this.patientService.getAvailableContactLenses().subscribe((data) => {
      console.log(data);
      this.availableContactLenses = data;
    });
  }
  handleAvailableEyeDiseases() {
    this.patientService.getAvailableEyeDiseases().subscribe((data) => {
      this.availableEyeDiseases = data;
    });
  }
  handleAvailableGeneralDiseases() {
    this.patientService.getAvailableGeneralDiseases().subscribe((data) => {
      this.availableGeneralDiseases = data;
    });
  }
  handleAvailableVisionConditions() {
    this.patientService.getAvailableVisionConditions().subscribe((data) => {
      this.availableVsionConditions = data;
    });
  }
  handleAvailableAppointmentReasons() {
    this.patientService.getAvailableAppointmentReasons().subscribe((data) => {
      this.availableAppointmentReasons = data;
    });
  }
  handlePatientsDetails() {
    const theId: number = +this.route.snapshot.paramMap.get('id')!;
    this.patientService.getPatient(theId).subscribe((data) => {
      this.patient = data;
    });
  }
  buildForm() {
    this.appointmentCartFormGroup = this.formBuilder.group({
      interview: this.formBuilder.group({
        appointmentReason: [false, ''],
        otherAppointmentReason: [''],
        otherEyeDisease: [''],
        otherGeneralDisease: [''],
        availableEyeDiseases: [false],
      }),
    });
  }
  get patientAge() {
    var rok = parseInt(this.patient.pesel.substring(0, 2), 10);
    var miesiac = parseInt(this.patient.pesel.substring(2, 4), 10) - 1;
    var dzien = parseInt(this.patient.pesel.substring(4, 6), 10);
    // Powszechnie uwaza sie, iz daty w numerach pesel obejmuja tylko ludzi urodzonych do 2000 roku. Na szczescie prawodawcy o tym pomysleli i do miesiaca dodawane sa liczby tak, by pesele starczyly az do 23 wieku.
    if (miesiac > 80) {
      rok = rok + 1800;
      miesiac = miesiac - 80;
    } else if (miesiac > 60) {
      rok = rok + 2200;
      miesiac = miesiac - 60;
    } else if (miesiac > 40) {
      rok = rok + 2100;
      miesiac = miesiac - 40;
    } else if (miesiac > 20) {
      rok = rok + 2000;
      miesiac = miesiac - 20;
    } else {
      rok += 1900;
    }
    var birthday = new Date();
    birthday.setFullYear(rok, miesiac, dzien);

    var timeDiff = Math.abs(Date.now() - birthday.getTime());
    var age = Math.floor(timeDiff / (1000 * 3600 * 24) / 365.25);
    return age;
  }

  onPlusMinusClick(element: any) {
    if (element.textContent == '+') {
      element.textContent = '-';
    } else {
      element.textContent = '+';
    }
  }
  saveAppointmentCard() {}
}

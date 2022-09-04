import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';
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
  selectedAppointmentReasons!: AppointmentReason[];

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
    this.availableAppointmentReasons =
      this.route.snapshot.data['availableAppointmentReasons'];
    this.availableGeneralDiseases =
      this.route.snapshot.data['availableGeneralDiseases'];
    this.availableVsionConditions =
      this.route.snapshot.data['availableVsionConditions'];
    this.handleAvailableContactLenses();

    this.buildForm();
  }
  buildForm() {
    this.appointmentCartFormGroup = this.formBuilder.group({
      interview: this.formBuilder.group({
        appointmentReason: this.addAvailableAppointmentReasonsControl(),
        otherAppointmentReason: [''],
        otherEyeDisease: [''],
        otherGeneralDisease: [''],
        availableEyeDiseases: this.addAvailableEyeDiseasesControl(),
        availableVsionConditions: [],
        otherNotes: [],
      }),
      refraction: this.formBuilder.group({
        rightEyeSignCurrent: [],
        rightEyeSphereCurrent: [],
        cylinderRightEyeCurrent: [],
        axisRightEyeCurrent: [],
        addRightEyeCurrent: [],
        VccRightEyeCurrent: [],
        VscRightEyeCurrent: [],
        leftEyeSignCurrent: [],
        leftEyeSphereCurrent: [],
        cylinderLeftEyeCurrent: [],
        axisLeftEyeCurrent: [],
        addLeftEyeCurrent: [],
        VccLeftEyeCurrent: [],
        VscLeftEyeCurrent: [],

        rightEyeSignNew: [],
        rightEyeSphereNew: [],
        cylinderRightEyeNew: [],
        axisRightEyeNew: [],
        addRightEyeNew: [],
        VccRightEyeNew: [],
        VscRightEyeNew: [],
        leftEyeSignNew: [],
        leftEyeSphereNew: [],
        cylinderLeftEyeSphereNew: [],
        axisLeftEyeNew: [],
        addLeftEyeNew: [],
        VccLeftEyeNew: [],
        VscLeftEyeNew: [],
      }),
    });
  }

  addAvailableAppointmentReasonsControl() {
    const arr = this.availableAppointmentReasons.map((element) => {
      return this.formBuilder.control(false);
    });
    return this.formBuilder.array(arr);
  }
  getSelectedAppointmentReasonsValue() {
    this.selectedAppointmentReasons = [];
    this.availableAppointmentReasonsArray.controls.forEach((control, i) => {
      if (control.value) {
        this.selectedAppointmentReasons.push(
          this.availableAppointmentReasons[i]
        );
      }
    });
    return this.selectedAppointmentReasons;
  }
  addAvailableEyeDiseasesControl() {
    const arr = this.availableEyeDiseases.map((element) => {
      return this.formBuilder.control(false);
    });
    return this.formBuilder.array(arr);
  }

  get availableEyeDiseasesArray() {
    return this.appointmentCartFormGroup.get(
      'interview.availableEyeDiseases'
    ) as FormArray;
  }
  get availableAppointmentReasonsArray() {
    return this.appointmentCartFormGroup.get(
      'interview.appointmentReason'
    ) as FormArray;
  }

  handleAvailableContactLenses() {
    this.patientService.getAvailableContactLenses().subscribe((data) => {
      console.log(data);
      this.availableContactLenses = data;
    });
  }

  handlePatientsDetails() {
    const theId: number = +this.route.snapshot.paramMap.get('id')!;
    this.patientService.getPatient(theId).subscribe((data) => {
      this.patient = data;
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

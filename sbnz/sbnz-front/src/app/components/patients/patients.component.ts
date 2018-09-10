import { Component, OnInit } from '@angular/core';
import { Patient } from '../../model/patient';
import { PatientService } from '../../services/patient/patient.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-patients',
  templateUrl: './patients.component.html',
  styleUrls: ['./patients.component.css']
})
export class PatientsComponent implements OnInit {

  patients: Patient[];

  constructor(private patientService: PatientService, private router: Router) { }

  ngOnInit() {
    this.getPatients();
  }

  getPatients() {
    this.patientService.getAllPatients().subscribe(data => {
      this.patients = data;
    })
  }

  diagnose(id: number) {
      this.router.navigate(['/diagnose', id]);
  }

  seeRecords(id: number) {
    this.router.navigate(['/records', id]);
}

}

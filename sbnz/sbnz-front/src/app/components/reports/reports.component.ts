import { Component, OnInit } from '@angular/core';
import { Patient } from '../../model/patient';
import { PatientService } from '../../services/patient/patient.service';
import { ReportDTO } from '../../model/reportDTO';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css']
})
export class ReportsComponent implements OnInit {

  showReport1: boolean = false;
  showReport2: boolean = false;
  showReport3: boolean = false;

  foundReports1: ReportDTO[];
  foundReports2: ReportDTO[];
  foundReports3: Patient[];

  constructor(private patientService: PatientService) { }

  ngOnInit() {
  }

  report1() {
    this.showReport2 = false;
    this.showReport3 = false;

    this.patientService.getReport1().subscribe(data => {
      this.foundReports1 = data,
      console.log(this.foundReports1);
    }
    )
  }

  report2() {
    this.showReport1 = false;
    this.showReport3 = false;

    this.patientService.getReport2().subscribe(data =>{
      this.foundReports2 = data,
      console.log(this.foundReports2);
    })
  }

  report3() {
    this.showReport1 = false;
    this.showReport2 = false;

    this.patientService.getReport3().subscribe(data =>{
      this.foundReports3 = data,
      console.log(this.foundReports3);
    })
  }

}

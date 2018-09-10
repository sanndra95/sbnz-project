import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PatientService } from '../../services/patient/patient.service';
import { MedicalRecord } from '../../model/medicalRecord';

@Component({
  selector: 'app-records',
  templateUrl: './records.component.html',
  styleUrls: ['./records.component.css']
})
export class RecordsComponent implements OnInit {

  records: MedicalRecord[];

  constructor(private route: ActivatedRoute, private patientService: PatientService) { }

  ngOnInit() {
    this.seeRecords(+this.route.snapshot.paramMap.get('id'));
  }

  seeRecords(id: number) {
    this.patientService.getRecords(id).subscribe(data => {
      console.log(data);
      this.records = data;
    });
  }

}

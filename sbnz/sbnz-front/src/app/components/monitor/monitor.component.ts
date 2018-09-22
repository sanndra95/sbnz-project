import { Component, OnInit } from '@angular/core';
import { PatientService } from '../../services/patient/patient.service';


@Component({
  selector: 'app-monitor',
  templateUrl: './monitor.component.html',
  styleUrls: ['./monitor.component.css']
})
export class MonitorComponent implements OnInit {

  constructor(private patientService: PatientService) {
    
   }

  ngOnInit() {
  }

  start() {
    this.patientService.monitor().subscribe();
  }

}

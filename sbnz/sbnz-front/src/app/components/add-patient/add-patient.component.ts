import { Component, OnInit } from '@angular/core';
import { Patient } from '../../model/patient';
import { PatientService } from '../../services/patient/patient.service';
import { Router } from '@angular/router';
import { MedicineService } from '../../services/medicine/medicine.service';
import { ComponentService } from '../../services/component/component.service';
import { Medicine } from '../../model/medicine';
import { MedicalComponent } from '../../model/medicalComponent';

@Component({
  selector: 'app-add-patient',
  templateUrl: './add-patient.component.html',
  styleUrls: ['./add-patient.component.css']
})
export class AddPatientComponent implements OnInit {

  medicine: Medicine[];
  components: MedicalComponent[];

  currentMedicine: Medicine = {
    id: null,
    name: "",
    type: ""
  }

  currentComponent: MedicalComponent = {
    id: null,
    name: ""
  }

  patient: Patient = {
    firstName: "",
    lastName: "",
    records: [],
    medicineAllergies: [],
    componentAllergies: []
  }

  constructor(private patientService: PatientService, 
    private medicineService: MedicineService, 
    private componentService: ComponentService,
    private router: Router) { }

  ngOnInit() {
    this.getMedicine();
    this.getComponents();
  }

  getMedicine() {
    this.medicineService.getAllMedicine().subscribe(data => {
      this.medicine = data;
    });
  }

  getComponents() {
    this.componentService.getAllComponents().subscribe(data => {
      this.components = data;
    })
  }

  removeMedicine(m: Medicine) {
    var index = this.patient.medicineAllergies.indexOf(m, 0);
    this.patient.medicineAllergies.splice(index, 1);
    this.medicine.push(m);
  }

  removeComponent(c: MedicalComponent) {
    var index = this.patient.componentAllergies.indexOf(c, 0);
    this.patient.componentAllergies.splice(index, 1);
    this.components.push(c);
  }

  addMedicine() {
    this.patient.medicineAllergies.push(this.currentMedicine);
    var index = this.medicine.indexOf(this.currentMedicine, 0);
    this.medicine.splice(index, 1);
  }

  addComponent() {
    this.patient.componentAllergies.push(this.currentComponent);
    var index = this.components.indexOf(this.currentComponent, 0);
    this.components.splice(index, 1);
  }

  addPatient() {
    this.patientService.addPatient(this.patient).subscribe(data => {
      console.log(this.patient);
      this.router.navigate(['/patients']);
    });
  }


}

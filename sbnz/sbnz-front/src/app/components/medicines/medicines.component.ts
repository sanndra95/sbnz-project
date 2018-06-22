import { Component, OnInit } from '@angular/core';
import { MedicineService } from '../../services/medicine/medicine.service';
import { Medicine } from '../../model/medicine';
import { ComponentService } from '../../services/component/component.service';
import { MedicalComponent } from '../../model/medicalComponent';

@Component({
  selector: 'app-medicines',
  templateUrl: './medicines.component.html',
  styleUrls: ['./medicines.component.css']
})
export class MedicinesComponent implements OnInit {

  addNew: Boolean = false;

  list: Medicine[];

  componentsList: MedicalComponent[];

  currentComponent: MedicalComponent = {
    id: null,
    name: ""
  }

  medicine: Medicine = {
    name: "",
    type: "",
    components: []
  }

  constructor(private medicineService: MedicineService, private componentService: ComponentService) { }

  ngOnInit() {
    this.getAll();
    this.getComponents();
  }

  getAll() {
    this.medicineService.getAllMedicine().subscribe(data => {
      this.list = data;
    });
  }

  getComponents() {
    this.componentService.getAllComponents().subscribe(data => {
      this.componentsList = data;
      console.log(this.componentsList);
    })
  }

  addComponent() {
    this.medicine.components.push(this.currentComponent);
    var index = this.componentsList.indexOf(this.currentComponent, 0);
    this.componentsList.splice(index, 1);
  }

  removeComponent(comp: MedicalComponent) {
    var index = this.medicine.components.indexOf(comp, 0);
    this.medicine.components.splice(index, 1);
    this.componentsList.push(comp);
  }

  addMedicine() {
    this.medicineService.createMedicine(this.medicine).subscribe(data =>
      this.getAll());
  }

  deleteMedicine(id : number, index : number) {
    console.log(id);
    this.medicineService.deleteMedicine(id).subscribe(data =>
    this.list.splice(index, 1));
  }

}

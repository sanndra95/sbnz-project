import { Component, OnInit } from '@angular/core';
import { Symptom } from '../../model/symptom';
import { SymptomService } from '../../services/symptom/symptom.service';

@Component({
  selector: 'app-symptoms',
  templateUrl: './symptoms.component.html',
  styleUrls: ['./symptoms.component.css']
})
export class SymptomsComponent implements OnInit {

  addNew : Boolean = false;

  list: Symptom[];

  symptom: Symptom = {
    name: "",
    type: ""
  }

  constructor(private symptomService : SymptomService) { }

  ngOnInit() {
    this.getAll();
  }

  getAll() {
    this.symptomService.getAllSymptoms().subscribe(data => {
      this.list = data;
    });
  }

  addSymptom() {
    this.symptomService.createSymptom(this.symptom).subscribe(data =>
      this.getAll());
  }

  deleteSymptom(id : number, index : number) {
    console.log(id);
    this.symptomService.deleteSymptom(id).subscribe(data =>
    this.list.splice(index, 1));
  }

  updateSymptom(id : number) {

  }

}

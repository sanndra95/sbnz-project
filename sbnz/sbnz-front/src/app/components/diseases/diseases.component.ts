import { Component, OnInit } from '@angular/core';
import { Disease } from '../../model/disease';
import { Symptom } from '../../model/symptom';
import { DiseaseService } from '../../services/disease/disease.service';
import { SymptomService } from '../../services/symptom/symptom.service';

@Component({
  selector: 'app-diseases',
  templateUrl: './diseases.component.html',
  styleUrls: ['./diseases.component.css']
})
export class DiseasesComponent implements OnInit {

  addNew: Boolean = false;

  list: Disease[];

  symptomsList: Symptom[];

  currentSymptom: Symptom = {
    id: null,
    name: "",
    type: ""
  }

  disease: Disease = {
    name: "",
    diseaseGroup: "",
    symptoms: []
  }

  constructor(private diseaseService: DiseaseService, private symptomService: SymptomService) { }

  ngOnInit() {
    this.getAll();
    this.getSymptoms();
  }

  getAll() {
    this.diseaseService.getAllDiseases().subscribe(data => {
      this.list = data;
    });
  }

  getSymptoms() {
    this.symptomService.getAllSymptoms().subscribe(data => {
      this.symptomsList = data;
      console.log(this.symptomsList);
    })
  }

  addSymptom() {
    this.disease.symptoms.push(this.currentSymptom);
    var index = this.symptomsList.indexOf(this.currentSymptom, 0);
    this.symptomsList.splice(index, 1);
  }

  removeSymptom(s: Symptom) {
    var index = this.disease.symptoms.indexOf(s, 0);
    this.disease.symptoms.splice(index, 1);
    this.symptomsList.push(s);
  }

  addDisease() {
    this.diseaseService.createDisease(this.disease).subscribe(data =>
      this.getAll());
  }

  deleteDisease(id : number, index : number) {
    console.log(id);
    this.diseaseService.deleteDisease(id).subscribe(data =>
    this.list.splice(index, 1));
  }

}

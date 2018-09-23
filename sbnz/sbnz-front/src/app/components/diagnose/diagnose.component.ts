import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Patient } from '../../model/patient';
import { PatientService } from '../../services/patient/patient.service';
import { MedicalRecord } from '../../model/medicalRecord';
import { Symptom } from '../../model/symptom';
import { SymptomService } from '../../services/symptom/symptom.service';
import { Disease } from '../../model/disease';
import { DiseaseService } from '../../services/disease/disease.service';
import { Medicine } from '../../model/medicine';
import { MedicineService } from '../../services/medicine/medicine.service';
import { DiseaseDTO } from '../../model/diseaseDTO';


@Component({
  selector: 'app-diagnose',
  templateUrl: './diagnose.component.html',
  styleUrls: ['./diagnose.component.css']
})
export class DiagnoseComponent implements OnInit {

  patient: Patient;
  allSymptoms: Symptom[];
  allDiseases: Disease[];
  allMedicine: Medicine[];

  diseasesBySymptomsList: DiseaseDTO[];

  startResoner: boolean = false;
  diseasesBySymptoms: boolean = false;
  symptomsByDisease: boolean = false;
  selfDiagnose: boolean = false;

  showResonerResult: boolean = false;
  showMedicineForm: boolean = false;
  showChosenMedicine: boolean = false;
  diagnosisConfirmation: boolean = false;

  patientIsAllergic: boolean;
  showAllergyMessage: boolean = false;
  

  showDiseasesTable: boolean = false;
  showSymptomsTable: boolean = false;

  diseaseChosen: boolean = false;


  record: MedicalRecord = {
    symptoms: []
  };

  chosenDisease: Disease;
  selfDiagnosedDisease: Disease;

  currentMedicine: Medicine;

  symptomsOfADisease: Symptom[];

  currentSymptom: Symptom = {
    id: null,
    name: "",
    type: ""
  }

  chosenTemperature: number;
  temp38: Symptom;
  temp40to41: Symptom;

  constructor(private patientService: PatientService, 
    private symptomService: SymptomService,
    private diseaseService: DiseaseService,
    private medicineService: MedicineService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit() {
    this.getPatient(+this.route.snapshot.paramMap.get('id'));
    this.getSymptoms();
    this.getDiseases();
    this.getMedicine();
  }

  showForm1() {
    this.diseasesBySymptoms = false;
    this.symptomsByDisease = false;
    this.selfDiagnose = false;
  }

  showForm2() {
    this.startResoner = false;
    this.symptomsByDisease = false;
    this.selfDiagnose = false;
  }

  showForm3() {
    this.startResoner = false;
    this.diseasesBySymptoms = false;
    this.selfDiagnose = false;
  }
    
  showForm4() {
    this.startResoner = false;
    this.diseasesBySymptoms = false;
    this.symptomsByDisease = false;
  }

  getPatient(id: number) {
    this.patientService.getPatient(id).subscribe(data => {
      console.log(data);
      this.patient = data;
    });
  }

  getSymptoms() {
    this.symptomService.getAllSymptoms().subscribe(data => {
      this.allSymptoms = data;
      for(var i = 0; i < this.allSymptoms.length; i++){
        if(this.allSymptoms[i].name == "Temperatura veca od 38C"){
          this.temp38 = this.allSymptoms[i];
          this.allSymptoms.splice(i, 1);
        }
        if(this.allSymptoms[i].name == "Temperatura od 40C do 41C"){
          this.temp40to41 = this.allSymptoms[i];
          this.allSymptoms.splice(i, 1);
        }
      }
    })
  }

  setTemperature() {
    if(!this.record.symptoms.includes(this.temp38) && this.chosenTemperature >= 38){
        this.record.symptoms.push(this.temp38);
      } 
    if(!this.record.symptoms.includes(this.temp40to41) && this.chosenTemperature >= 40 && this.chosenTemperature <= 41){
        this.record.symptoms.push(this.temp40to41);  
    }
    }

  getDiseases() {
    this.diseaseService.getAllDiseases().subscribe(data => {
      this.allDiseases = data;
    })
  }

  getMedicine() {
    this.medicineService.getAllMedicine().subscribe(data => {
      this.allMedicine = data;
    })
  }

  addSymptom() {
    this.record.symptoms.push(this.currentSymptom);
    var index = this.allSymptoms.indexOf(this.currentSymptom, 0);
    this.allSymptoms.splice(index, 1);
  }

  removeSymptom(s: Symptom) {
    var index = this.record.symptoms.indexOf(s, 0);
    var temp = this.record.symptoms[index];
    this.record.symptoms.splice(index, 1);
    if(temp != this.temp38 && temp != this.temp40to41) {
      this.allSymptoms.push(s);
    }
    
  }

  getDiagnose() {
    console.log(this.record);
    this.patientService.getDiagnose(this.record, this.patient.id).subscribe(data => {
      this.record = data;
      this.showResonerResult = true;
      this.showMedicineForm = true;

    });
  }

  addMedicine() {
    this.record.medicine = this.currentMedicine;
    this.showChosenMedicine = true;
    
    console.log(this.record.medicine);
    this.medicineService.checkForAllergies(this.patient.id, this.record.medicine).subscribe(data => {
        this.patientIsAllergic = data;
        console.log(this.patientIsAllergic);

        if(this.patientIsAllergic) {
          this.showAllergyMessage = true;
          this.diagnosisConfirmation = false;
        }
        else {
          this.diagnosisConfirmation = true;
          this.showAllergyMessage = false;
        }
    });

    
  }

  confirmDiagnosis() {
    this.patientService.createRecord(this.record, this.patient.id).subscribe(data => {
      this.showResonerResult = false;
      this.diseasesBySymptoms = false;
      this.symptomsByDisease = false;
      this.showMedicineForm = false;
      this.diagnosisConfirmation = false;
      this.startResoner = false;
      this.showDiseasesTable = false;
      this.diseaseChosen = false;
      this.showSymptomsTable = false;
      this.selfDiagnose = false;

      for(var i = 0; i < this.record.symptoms.length; i++) {
        if(this.record.symptoms[i] != this.temp38 && this.record.symptoms[i] != this.temp40to41) {
          this.allSymptoms.push(this.record.symptoms[i]);
        }
        
      }
      this.record = {
        symptoms: []
      }
      alert("Diagnosis confirmed.");
    });
  }

  getDiseasesBySymptoms() {
    console.log(this.record.symptoms);
    this.diseaseService.getDiseasesBySymptoms(this.record.symptoms).subscribe(data => {
      this.diseasesBySymptomsList = data,
      this.showDiseasesTable = true,
      console.log(this.diseasesBySymptomsList);
    });
  }

  chooseDisease(d: Disease) {
    console.log(d);
    this.record.disease = d;
    this.diseaseChosen = true;
    this.showMedicineForm = true;
  }

  getSymptomsByDisease() {
    this.symptomService.getSymptomsByDisease(this.chosenDisease).subscribe(data => {
      this.symptomsOfADisease = data;
      this.showSymptomsTable = true;
    });
  }

}

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
    })
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
    this.record.symptoms.splice(index, 1);
    this.allSymptoms.push(s);
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
    this.diagnosisConfirmation = true;
    console.log(this.record);
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

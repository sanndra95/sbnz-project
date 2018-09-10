import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Patient } from '../../model/patient';
import { MedicalRecord } from '../../model/medicalRecord';
import { Observer } from 'rxjs';
import { Observable } from 'rxjs/Observable';

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  constructor(private http: HttpClient) {  }

  addPatient(patient: Patient) {
    let headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    return this.http.post("http://localhost:8080/api/patient/create", patient,
    {
      headers: headers
    });
  }

  getAllPatients() {
    return this.http.get<Patient[]>("http://localhost:8080/api/patient/getAll");
  }

  getPatient(id: number) {
    return this.http.get<Patient>("http://localhost:8080/api/patient/get/" + id);
  }

  getDiagnose(record: MedicalRecord, id: number): Observable<MedicalRecord> {
    let headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    return this.http.post<MedicalRecord>("http://localhost:8080/api/medicalRecord/search/" + id, record,
    {
      headers: headers
    });
  } 

  createRecord(record: MedicalRecord, id: number): Observable<MedicalRecord> {
    let headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    return this.http.post<MedicalRecord>("http://localhost:8080/api/medicalRecord/create/" + id, record,
    {
      headers: headers
    });
  } 

  getRecords(id: number) {
    return this.http.get<MedicalRecord[]>("http://localhost:8080/api/medicalRecord/getAll/" + id);
  }

}

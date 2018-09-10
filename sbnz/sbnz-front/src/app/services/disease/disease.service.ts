import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Disease } from '../../model/disease';
import { Symptom } from '../../model/symptom';
import { DiseaseDTO } from '../../model/diseaseDTO';

@Injectable()
export class DiseaseService {

  constructor(private http: HttpClient) { }

  getAllDiseases(): Observable<Disease[]> {
    return this.http.get<Disease[]>("http://localhost:8080/api/disease/getAll");
  }

  createDisease(disease : Disease) {
    let headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    return this.http.post("http://localhost:8080/api/disease/create", disease,
    {
      headers: headers
    });
  }

  deleteDisease(id : number) {
    return this.http.delete("http://localhost:8080/api/disease/delete/" + id);
  }

  getDiseasesBySymptoms(symptoms: Symptom[]): Observable<DiseaseDTO[]> {
    let headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    return this.http.post<DiseaseDTO[]>("http://localhost:8080/api/disease/getBySymptoms", symptoms,
    {
      headers: headers
    });

  }

}

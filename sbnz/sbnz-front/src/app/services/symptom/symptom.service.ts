import { Injectable } from '@angular/core';
import { Symptom } from '../../model/symptom';
import { Observable } from 'rxjs/Observable';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Disease } from '../../model/disease';


@Injectable()
export class SymptomService {

  constructor(private http: HttpClient) { }

  getAllSymptoms(): Observable<Symptom[]> {
    return this.http.get<Symptom[]>("http://localhost:8080/api/symptom/getAll");
  }

  createSymptom(symptom : Symptom) {
    let headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    return this.http.post("http://localhost:8080/api/symptom/create", symptom,
    {
      headers: headers
    });
  }

  deleteSymptom(id : number) {
    return this.http.delete("http://localhost:8080/api/symptom/delete/" + id);
  }

  getSymptomsByDisease(disease: Disease): Observable<Symptom[]> {
    let headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    return this.http.post<Symptom[]>("http://localhost:8080/api/symptom/getByDisease", disease,
    {
      headers: headers
    });

  }


}

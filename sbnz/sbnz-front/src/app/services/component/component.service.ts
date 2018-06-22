import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MedicalComponent } from '../../model/medicalComponent';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class ComponentService {

  constructor(private http: HttpClient) { }

  getAllComponents(): Observable<MedicalComponent[]> {
    return this.http.get<MedicalComponent[]>("http://localhost:8080/api/component/getAll");
  }

  createComponent(component : MedicalComponent) {
    let headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    return this.http.post("http://localhost:8080/api/component/create", component,
    {
      headers: headers
    });
  }

  deleteComponent(id : number) {
    return this.http.delete("http://localhost:8080/api/component/delete/" + id);
  }
}

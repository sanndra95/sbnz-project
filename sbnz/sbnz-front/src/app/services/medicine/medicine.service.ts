import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Medicine } from '../../model/medicine';

@Injectable({
  providedIn: 'root'
})
export class MedicineService {

  constructor(private http: HttpClient) { }

  getAllMedicine(): Observable<Medicine[]> {
    return this.http.get<Medicine[]>("http://localhost:8080/api/medicine/getAll");
  }

  createMedicine(medicine : Medicine) {
    let headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    return this.http.post("http://localhost:8080/api/medicine/create", medicine,
    {
      headers: headers
    });
  }

  deleteMedicine(id : number) {
    return this.http.delete("http://localhost:8080/api/medicine/delete/" + id);
  }
}

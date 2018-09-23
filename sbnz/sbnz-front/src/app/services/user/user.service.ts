import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/map';
import { User } from '../../model/user';


@Injectable()
export class UserService {

  constructor(private http:HttpClient) { }

  authenticate(username: string, password: string) {
    let authenticationRequest = { username: username, password: password };
    let params = JSON.stringify(authenticationRequest);
    let headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    return this.http.post("http://localhost:8080/auth", params,
      {
        headers: headers
      }).map(res => res);
  }

  registerDoctor(doctor: User) {
    let headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    return this.http.post("http://localhost:8080/api/user/registerDoctor", doctor,
    {
      headers: headers
    });
  }

  logout() {
    return this.http.get("http://localhost:8080/api/user/logout");
  }
}

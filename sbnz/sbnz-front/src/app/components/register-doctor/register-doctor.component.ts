import { Component, OnInit } from '@angular/core';
import { User } from '../../model/user';
import { UserService } from '../../services/user/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register-doctor',
  templateUrl: './register-doctor.component.html',
  styleUrls: ['./register-doctor.component.css']
})
export class RegisterDoctorComponent implements OnInit {

  doctor: User = {
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    role: "ROLE_DOCTOR"
  }

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit() {
  }

  registerDoctor() {
    this.userService.registerDoctor(this.doctor).subscribe(data => {
      console.log(this.doctor);
      this.router.navigate(['/home']);
    });
  }

}

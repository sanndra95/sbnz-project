import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username : string;
  password : string;

  constructor(private userService : UserService, private router : Router) {
   }

  ngOnInit() {
  }

  login(){
    this.userService.authenticate(this.username, this.password).subscribe(
      data=>{ 
        localStorage.setItem("loggedUser", JSON.stringify(data));
          this.router.navigate(['/home']);
      }
    );

  }

}

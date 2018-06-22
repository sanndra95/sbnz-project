import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoggedUtils } from './utils/logged.utils';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';

  constructor(private router:Router){}

  ngOnInit(){}

  isLoggedIn(){
    return !LoggedUtils.isEmpty();
  }

  logout(){
    localStorage.removeItem("loggedUser");
    this.router.navigate(['/']);
  }

  getRole(){
    return LoggedUtils.getRole();
  }

}

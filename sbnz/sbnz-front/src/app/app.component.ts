import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoggedUtils } from './utils/logged.utils';
import Stomp from 'stompjs';
import SockJS from 'sockjs-client';
import $ from 'jquery';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';

  message1: string;
  private serverUrl = 'http://localhost:8080/socket';
  private stompClient;

  constructor(private router:Router){
    let ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(ws);
    this.stompClient.connect({}, frame =>{
      this.stompClient.subscribe('/chat', message =>{
        this.message1 = message.body;
        $("#card").append("<div class='message'>"+message.body+"</div>")
        //console.log(message.body);
      });
    });
    
  }

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

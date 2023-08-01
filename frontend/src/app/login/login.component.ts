import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  message = ''; 

  constructor (private http: HttpClient, private router:Router) {
    
	}

  login(username: string, password:string) {
    const credentials = {
  		login: username,
  		password: password
		};

      const headers = new HttpHeaders(credentials ? {
        authorization : 'Basic ' + btoa(credentials.login + ':' + credentials.password)
    } : {});

    

    this.http.get('http://localhost:8080/user', { headers: headers }).subscribe(
  (response: any) => {
      sessionStorage.setItem('auth', 'true');
      sessionStorage.setItem('login', credentials.login);
      sessionStorage.setItem('password', credentials.password);
    

      this.http.get<any>("http://localhost:8080/userByLogin/" + credentials.login, { headers }).subscribe
      ((res)=>{
        sessionStorage.setItem('userId', res.id);
        sessionStorage.setItem('role', res.role);
        sessionStorage.setItem('firstName', res.firstName);
        sessionStorage.setItem('lastName', res.lastName);
      });

      this.router.navigateByUrl("/main");  
  },
  (error: any) => {
    sessionStorage.setItem('auth', 'false');
    this.message = "Invalid login or password";
  }
);
  }
}

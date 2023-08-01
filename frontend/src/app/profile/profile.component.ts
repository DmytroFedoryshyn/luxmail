import { Router } from '@angular/router';
import { MailService } from '../mail.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit, ViewChild, ElementRef  } from '@angular/core';


@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  name: any = "";
  @ViewChild('checkbox', {static: true}) checkbox!: ElementRef;
  @ViewChild('fn', {static: true}) firstName!: ElementRef;
  @ViewChild('ln', {static: true}) lastName!: ElementRef;


  ngOnInit() {
    this.firstName.nativeElement.value = sessionStorage.getItem('firstName');
    this.lastName.nativeElement.value = sessionStorage.getItem('lastName');
    this.name = sessionStorage.getItem('login');
    const role = sessionStorage.getItem('role');
    
    if (role === 'APICALL') {
     this.checkbox.nativeElement.checked = true;
    } else {
      this.checkbox.nativeElement.checked = false;
    }
  }

  constructor(private http: HttpClient, private router:Router, private mailService:MailService) {
    
  }

  signOut() {
    sessionStorage.removeItem('login');
    sessionStorage.removeItem('password');
    sessionStorage.setItem('auth', 'false');
    this.router.navigateByUrl("/login");  
  }

  save() {
    sessionStorage.setItem('firstName', this.firstName.nativeElement.value);
    sessionStorage.setItem('lastName', this.lastName.nativeElement.value);
    if (this.checkbox.nativeElement.checked) {
      sessionStorage.setItem('role', 'APICALL');
    } else {
      sessionStorage.setItem('role', 'USER');
    }

    const user = {
      firstName: sessionStorage.getItem('firstName'),
      lastName: sessionStorage.getItem('lastName'),
      email: sessionStorage.getItem('login'),
      password: sessionStorage.getItem('password'),
      role: sessionStorage.getItem('role'),
    };


    this.mailService.updateUser(user, sessionStorage.getItem('userId')).subscribe(
      res => {
        console.log(res);
      }
    );
    this.router.navigateByUrl("/main");  

}

  close() {
      this.router.navigateByUrl("/main");  
  }
}

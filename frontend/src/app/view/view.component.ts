import { Component, OnInit, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
import { MailService } from '../mail.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.css']
})
export class ViewComponent implements AfterViewInit {
  @ViewChild('title', {static: true}) title!: ElementRef;
  @ViewChild('from_to', {static: true}) from_to!: ElementRef;
  @ViewChild('text', {static: true}) text!: ElementRef;

  navigation: any;

  constructor (private route: ActivatedRoute, private http: HttpClient, private router:Router, private mailService:MailService) {
    this.navigation = this.router.getCurrentNavigation();
  }

  ngAfterViewInit() {
    if (this.navigation && this.navigation.extras && this.navigation.extras.state) {
      const mail = this.navigation.extras.state['data'];
      this.title.nativeElement.innerText = mail.title;
      if (mail.isIncoming) {
        const sender = mail.sender;
          this.from_to.nativeElement.innerText = "From: " + sender.firstName + " " + sender.lastName;
      } else {
        const recipientsNames = mail.recipients.map((recipient: { firstName: string; lastName: string }) => `${recipient.firstName} ${recipient.lastName}`);
        this.from_to.nativeElement.innerText = "To: " + recipientsNames.join(', ');
      }

      this.text.nativeElement.value = mail.content;
    }
  }

  signOut() {
    sessionStorage.removeItem('login');
    sessionStorage.removeItem('password');
    sessionStorage.setItem('auth', 'false');
    this.router.navigateByUrl("/login");  
  }

  reply() {
    const mail = this.navigation.extras.state['data'];
    this.router.navigate(['/write'], { state: { data: mail } });
  }

  close() {
    this.router.navigateByUrl("/main");  
  }
}

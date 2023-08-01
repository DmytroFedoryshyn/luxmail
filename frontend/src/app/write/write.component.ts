import { Component, OnInit, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
import { MailService } from '../mail.service';
import { ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-write',
  templateUrl: './write.component.html',
  styleUrls: ['./write.component.css']
})
export class WriteComponent implements AfterViewInit {
  @ViewChild('res', {static: true}) recipient!: ElementRef;
  @ViewChild('title', {static: true}) title!: ElementRef;
  navigation: any;

  constructor (private route: ActivatedRoute, private router:Router, private mailService:MailService) {
    this.navigation = this.router.getCurrentNavigation();
  }

  ngAfterViewInit() {
    if (this.navigation && this.navigation.extras && this.navigation.extras.state) {
      const mail = this.navigation.extras.state['data'];
      if (!mail.isIncoming) {
        const recipientsNames = mail.recipients.map((recipient: { email: string }) => `${recipient.email}`);
        this.recipient.nativeElement.value = recipientsNames.join(', ');
      } else {
        this.recipient.nativeElement.value = mail.sender.email;
      }
      
      this.title.nativeElement.value = mail.title;
    }
  }

  signOut() {
    sessionStorage.removeItem('login');
    sessionStorage.removeItem('password');
    sessionStorage.setItem('auth', 'false');
    this.router.navigateByUrl("/login");  
  }

  send(res:string, title:string, text:string) {
    let replyToId = null;
    if (this.navigation && this.navigation.extras && this.navigation.extras.state) {
      const mail = this.navigation.extras.state['data'];
      replyToId = mail.id;
    }

    const data = {
      senderId: sessionStorage.getItem('userId'),
      recipients: res.split(', '),
      title: title,
      content: text, 
      replyToId: replyToId,
    };

    this.mailService.send(data).subscribe(
      (response: boolean) => {
        if (response == false) {
          alert('Check your recipient list and try again');
        } else {
          this.router.navigateByUrl("/main");  
        }
      }
    );
  }

  cancel() {
    this.router.navigateByUrl("/main");  
  }
}

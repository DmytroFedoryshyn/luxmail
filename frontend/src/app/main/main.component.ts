import { Component, OnInit, ViewChild, ElementRef  } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { forkJoin } from 'rxjs';
import { Router, NavigationExtras  } from '@angular/router';
import { MailService } from '../mail.service';
import { ViewComponent } from '../view/view.component';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})

export class MainComponent {
  @ViewChild('inputSearch', {static: true}) inputSearch!: ElementRef;

  list: any[] = []
  
  constructor (private http: HttpClient, private router:Router, private mailService:MailService) {
    this.fullList();
  }

  clear() {
    this.fullList();
    this.inputSearch.nativeElement.value = "";
  }

  fullList() {
    this.mailService.getMails().subscribe(
      (mails) => {
        this.list = mails;
      },
      (error) => {
        console.error('Error fetching mails:', error);
      }
    );
  }


search(key: string) {
  this.mailService.search(key).subscribe(
    (mails) => {
      this.list = mails;
      console.log(mails);
    },
    (error) => {
      console.error('Error fetching mails:', error);
    }
  );

  
}

deleteSelected() {
  if(!confirm("Are you sure to delete selected items?")) {
      return;
  }

  this.mailService.deleteItem(this.list);

  window.location.reload();
}

signOut() {
  sessionStorage.removeItem('login');
  sessionStorage.removeItem('password');
  sessionStorage.setItem('auth', 'false');
  this.router.navigateByUrl("/login");  
}

write() {
  this.router.navigateByUrl("/write");  
}

onRowClick(obj:any) {
 
  this.router.navigate(['/view'], { state: { data: obj } });
}

goToProfile() {
  this.router.navigate(['/profile']);
}
}

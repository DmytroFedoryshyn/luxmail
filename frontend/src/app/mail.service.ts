import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class MailService {

  constructor(private http: HttpClient) { }

  public getHeaders(): HttpHeaders {
    const login = sessionStorage.getItem('login');
    const password = sessionStorage.getItem('password');
    const credentials = login && password ? btoa(`${login}:${password}`) : '';
    return new HttpHeaders({ authorization: `Basic ${credentials}` });
  }

  getMails(): Observable<any[]> {
    return this.http.get<any[]>('http://localhost:8080/mails', { headers: this.getHeaders() })
      .pipe(
        map(response => response.map(item => {
          const incoming = item.recipients.some((user: any) => user.id == sessionStorage.getItem('userId'));
          return {
            id: item.id,
            sender: item.sender,
            recipients: item.recipients,
            title: item.title,
            isIncoming: incoming,
            selected: false,
            content: item.content,
            replyTo: item.replyTo,
            date: item.timestamp
          };
        }))
      );
  }

  deleteItem(list: any[]) {
    list.forEach((element) => {
      if (element.selected) {
        this.http.delete<any[]>("http://localhost:8080/mail/" + element.id, { headers: this.getHeaders() }).subscribe();
      }
    });

    window.location.reload();
  }

  search(searchString: string): Observable<any[]> {
    return this.http.post<any[]>('http://localhost:8080/mails/' + searchString, null, { headers: this.getHeaders() })
      .pipe(
        map(response => response.map(item => {
          const incoming = item.recipients.some((user: any) => user.id == sessionStorage.getItem('userId'));
          return {
            id: item.id,
            sender: item.sender,
            recipients: item.recipients,
            title: item.title,
            isIncoming: incoming,
            selected: false,
            content: item.content,
            replyTo: item.replyTo,
            date: item.timestamp
          };
        }))
      );
  }

  send(data:any) {
    return this.http.post<boolean>('http://localhost:8080/mail', data, { headers: this.getHeaders() });
  }

  updateUser(data:any, id: any) {
    return this.http.put<any>('http://localhost:8080/user/'+id, data, { headers: this.getHeaders() });
  }
  
}

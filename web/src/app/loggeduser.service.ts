import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoggedUserService {

  constructor(private http: HttpClient) { }

  getLoggedUser(): Observable<string> {
    return this.http.get<string>('/api/loggeduser');
  }

}

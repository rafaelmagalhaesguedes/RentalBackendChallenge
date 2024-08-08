// reservation.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  getGroups(): Observable<any> {
    return this.http.get(`${this.apiUrl}/groups`);
  }

  getAccessories(): Observable<any> {
    return this.http.get(`${this.apiUrl}/accessories`);
  }
}
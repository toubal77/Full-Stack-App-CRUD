import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Utilisateur } from '../core/models/Utilisateur';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthServiceService {
  private apiUrl = '/api/auth';

  constructor(private http: HttpClient) {}

  private isLoggedIn = new BehaviorSubject<boolean>(this.hasToken());
  isLoggedIn$ = this.isLoggedIn.asObservable();

  private hasToken(): boolean {
    return !!sessionStorage.getItem('token');
  }

  setLoginStatus(isLoggedIn: boolean) {
    this.isLoggedIn.next(isLoggedIn);
  }

  login(username: string, password: string) {
    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded',
    });
    const body = new HttpParams()
      .set('username', username)
      .set('password', password);

    return this.http.post(this.apiUrl + '/login', body.toString(), {
      headers,
      observe: 'response',
    });
  }

  logout() {
    sessionStorage.removeItem('token');
    this.setLoginStatus(false);
  }

  getToken(): string | null {
    return sessionStorage.getItem('token');
  }
}

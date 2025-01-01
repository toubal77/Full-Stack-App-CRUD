import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Utilisateur } from '../core/models/Utilisateur';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {

  private apiUrl = 'http://localhost:8080/api/auth';

  private isLoggedIn = new BehaviorSubject<boolean>(this.hasToken());
  isLoggedIn$ = this.isLoggedIn.asObservable();

  private isAdmin = new BehaviorSubject<boolean>(this.hasAdminRole());
  isAdmin$ = this.isAdmin.asObservable();

  constructor(private http: HttpClient, private router: Router) { }

  private hasToken(): boolean {
    return !!sessionStorage.getItem('token');
  }

  private hasAdminRole(): boolean {
    const adminRole = sessionStorage.getItem('adminRole') === "true";
    return adminRole;
  }

  setLoginStatus(isLoggedIn: boolean) {
    this.isLoggedIn.next(isLoggedIn);
  }

  setHasAdminRole(hasAdminRole: boolean) {
    this.isAdmin.next(hasAdminRole);
  }

  login(username: string, password: string) {
    const headers = new HttpHeaders({ 'Content-Type': 'application/x-www-form-urlencoded' });
    const body = new HttpParams()
      .set('username', username)
      .set('password', password);
    return this.http.post(this.apiUrl + '/login', body.toString(), { headers, observe: 'response' });
  }

  logout() {
    this.setLoginStatus(false);
    this.setHasAdminRole(false);
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return sessionStorage.getItem('token');
  }

  getAdminRole(): boolean {
    return this.isAdmin.getValue();
  }
}

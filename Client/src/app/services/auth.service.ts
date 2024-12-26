import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Utilisateur } from '../core/models/Utilisateur';

@Injectable({
  providedIn: 'root',
})
export class AuthServiceService {
  private apiUrl = '/api/auth';

  constructor(private http: HttpClient, private router: Router) {}

  login(username: string, password: string) {
    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded',
    });
    const body = new HttpParams()
      .set('username', username)
      .set('password', password);

    return this.http
      .post(this.apiUrl + '/login', body.toString(), {
        headers,
        observe: 'response',
      })
      .subscribe((response) => {
        const token = response.headers.get('Authorization')?.split(' ')[1];
        if (token) {
          sessionStorage.setItem('token', token);
          console.log('voila le token ' + token);
          console.log('voila la reponse ' + response.body);
          const utilisateur = new Utilisateur(response.body as Utilisateur);
          console.log('Utilisateur connecté :', utilisateur);
          this.router.navigate(['/']);
        }
      });
  }

  logout() {
    sessionStorage.removeItem('token');
  }

  getToken(): string | null {
    return sessionStorage.getItem('token');
  }
}

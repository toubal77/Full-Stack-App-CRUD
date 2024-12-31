import { Component } from '@angular/core';
import { catchError, of } from 'rxjs';
import { Route, Router } from '@angular/router';
import { Utilisateur } from '../core/models/Utilisateur';
import { AuthServiceService } from '../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(
    private authService: AuthServiceService,
    private router: Router
  ) { }
  onSubmit() {
    this.errorMessage = '';
    console.log("Nom d'utilisateur:", this.username);
    console.log('Mot de passe:', this.password);

    this.authService
      .login(this.username, this.password)
      .pipe(
        catchError((error) => {
          this.handleError(error);
          return of(null);
        })
      )
      .subscribe({
        next: (response) => {
          const token = response?.headers.get('Authorization')?.split(' ')[1];
          if (token) {
            sessionStorage.setItem('token', token);
            console.log('voila le token ' + token);
            console.log('voila la reponse ' + response.body);

            const utilisateur = new Utilisateur(response.body as Utilisateur);
            console.log('Utilisateur connecté :', utilisateur);

            this.authService.setLoginStatus(true);

            this.router.navigate(['/']);
          }
        },
        error: (err) => {
          console.error('Erreur de connexion:', err);
        },
      });
  }

  private handleError(error: any): void {
    if (error.status === 401) {
      this.errorMessage = "Nom d'utilisateur ou mot de passe incorrect.";
    } else {
      this.errorMessage = 'Une erreur est survenue, veuillez réessayer.';
    }
  }
}

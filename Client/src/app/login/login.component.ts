import { Component } from '@angular/core';
import { catchError, of } from 'rxjs';
import { Route, Router } from '@angular/router';
import { Utilisateur } from '../core/models/Utilisateur';
import { AuthServiceService } from '../services/auth.service';
import { jwtDecode } from 'jwt-decode';

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

  getRolesFromToken(token: string): string[] {
    const decodedToken: any = jwtDecode(token);
    console.log(decodedToken);
    const roles = decodedToken?.resource_access?.['realm-management']?.roles || [];
    return roles;
  }

  onSubmit() {
    this.errorMessage = '';

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

            const roles = this.getRolesFromToken(token);
            console.log('Rôles de l\'utilisateur :', roles);
            const adminRole = roles.includes('realm-admin');
            console.log('adminRole ', adminRole);
            sessionStorage.setItem('adminRole', adminRole.toString());

            const utilisateur = new Utilisateur(response.body as Utilisateur);

            this.authService.setLoginStatus(true);
            this.authService.setHasAdminRole(adminRole); 
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

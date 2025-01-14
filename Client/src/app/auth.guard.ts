import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { AuthServiceService } from './services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard {

  constructor(
    private authService: AuthServiceService,
    private router: Router
  ) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> {
    return this.authService.isAdmin$.pipe(
      map((isAdmin) => {
        const token = sessionStorage.getItem('token'); 

        if (!token) {
          this.router.navigate(['/login'], { queryParams: { returnUrl: state.url } });
          return false;
        }
        if (isAdmin) {
          return true;
        } else {
          this.router.navigate(['/error']);
          return false;
        }
      })
    );
  }
}

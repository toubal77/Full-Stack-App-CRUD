import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthServiceService } from '../services/auth.service';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AdminRoleGuard {

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

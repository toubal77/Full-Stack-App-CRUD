import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit {
  isLoggedIn: boolean = false;
  constructor(
    private router: Router,
    private authService: AuthServiceService
  ) {}
  ngOnInit() {
    this.checkToken();
  }
  checkToken() {
    this.authService.isLoggedIn$.subscribe((status) => {
      this.isLoggedIn = status;
    });
  }

  Login() {
    this.router.navigate(['/login']);
  }

  loginOrLogout() {
    if (this.isLoggedIn) {
      sessionStorage.removeItem('token');
      this.isLoggedIn = false;
      this.authService.setLoginStatus(false);
      this.router.navigate(['/login']);
    } else {
      this.router.navigate(['/login']);
      this.authService.setLoginStatus(true);
    }
  }
}

import { Component } from '@angular/core';
import { AuthServiceService } from '../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  email: string = '';
  password: string = '';

  constructor(private authService: AuthServiceService) {}
  onSubmit() {
    console.log('email:', this.email);
    console.log('mdps :', this.password);
    this.authService.login(this.email, this.password);
  }
}

import { Component } from '@angular/core';

@Component({
  selector: 'app-error-page',
  templateUrl: './error-page.component.html',
  styleUrls: ['./error-page.component.scss']
})
export class ErrorPageComponent {
  message: string = 'Vous n\'avez pas les droits requis pour consulter cette page. Veuillez contacter l\'administrateur du site pour obtenir l\'accès.';
}

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListCategoriesComponent } from './list-categories/list-categories.component';

const routes: Routes = [
  { path: '', redirectTo: '/list-categories', pathMatch: 'full' }, // redirection par defaut
  { path: 'list-categories', component: ListCategoriesComponent }, 
  { path: '**', redirectTo: '/list-categories' } // redirection route non connues
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

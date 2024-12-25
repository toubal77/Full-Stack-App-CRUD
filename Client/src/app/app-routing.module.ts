import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListCategoriesComponent } from './list-categories/list-categories.component';
import { Page404Component } from './layout/page-404/page-404.component';
import { CreateCategoryComponent } from './create-category/create-category.component';
import { ContactComponent } from './contact/contact.component';
import { SwaggerViewerComponent } from './swagger-viewer/swagger-viewer.component';

const routes: Routes = [
  { path: '', redirectTo: '/list-categories', pathMatch: 'full' }, // redirection par defaut
  { path: 'list-categories', component: ListCategoriesComponent }, 
  { path: 'create-categorie', component: CreateCategoryComponent }, 
  { path: 'edit-categorie/:id', component: CreateCategoryComponent },
  { path: 'contact', component: ContactComponent },
  { path: 'swagger', component: SwaggerViewerComponent },
  // { path: '**', redirectTo: '/list-categories' }, // redirection route non connues
  { path: '**', component: Page404Component }
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

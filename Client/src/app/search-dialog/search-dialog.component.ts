import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { CategoryService } from '../services/category.service';
import { Category } from '../core/models/Category';

@Component({
  selector: 'app-search-dialog',
  templateUrl: './search-dialog.component.html',
})
export class SearchDialogComponent {
  isRootCategory: boolean = false;
  creationDateAfter: string | null = null;
  creationDateBefore: string | null = null;
  creationDateFrom: string | null = null;
  creationDateTo: string | null = null;
  childCount: number | null = null;
  categories: Category[] = [];

  constructor(private dialogRef: MatDialogRef<SearchDialogComponent>, private categoryService: CategoryService) {}

  applyFilters() {
    const filters = {
      isRootCategory: this.isRootCategory,
      creationDateAfter: this.creationDateAfter || '',
      creationDateBefore: this.creationDateBefore || '', 
      creationDateFrom: this.creationDateFrom || '', 
      creationDateTo: this.creationDateTo || '',
      childCount: this.childCount != null ? this.childCount : ''
    };
  
    console.log('filters applique:', filters);
  
    const startDate = filters.creationDateFrom || filters.creationDateAfter; 
    const endDate = filters.creationDateTo || filters.creationDateBefore; 
  
    this.categoryService.filterCategories(filters.isRootCategory, startDate, endDate, this.childCount).subscribe({
      next: (data: Category[]) => {
        this.categories = data;
        console.log('categories', this.categories);
        this.dialogRef.close(this.categories);
      },
      error:(error) => {
        console.error('Erreur lors de la récupération des catégories:', error.error.message);
      }
    });
  }  
}

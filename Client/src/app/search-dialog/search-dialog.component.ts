import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { CategoryService } from '../services/category.service';
import { Category } from '../core/models/Category';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-search-dialog',
  templateUrl: './search-dialog.component.html',
  styleUrls: ['./search-dialog.component.scss']
})
export class SearchDialogComponent {
  isRootCategory: boolean = false;
  creationDateAfter: string | null = null;
  creationDateBefore: string | null = null;
  creationDateFrom: string | null = null;
  creationDateTo: string | null = null;
  childCount: number | null = null;
  categories: Category[] = [];
  today: string;

  constructor(
    private datePipe: DatePipe,
    private dialogRef: MatDialogRef<SearchDialogComponent>,
    private categoryService: CategoryService
  ) {
    this.today = this.datePipe.transform(new Date(), 'yyyy-MM-dd') || '';
  }

  formatDate(date: Date): string {
    return this.datePipe.transform(date, 'yyyy-MM-dd') || '';
  }

  applyFilters() {
    if (this.creationDateAfter) {
      this.creationDateAfter = this.formatDate(new Date(this.creationDateAfter));
    }
    if (this.creationDateBefore) {
      this.creationDateBefore = this.formatDate(new Date(this.creationDateBefore));
    }
    if (this.creationDateFrom) {
      this.creationDateFrom = this.formatDate(new Date(this.creationDateFrom));
    }
    if (this.creationDateTo) {
      this.creationDateTo = this.formatDate(new Date(this.creationDateTo));
    }

    const filters = {
      isRootCategory: this.isRootCategory,
      creationDateAfter: this.creationDateAfter,
      creationDateBefore: this.creationDateBefore,
      creationDateFrom: this.creationDateFrom,
      creationDateTo: this.creationDateTo,
      childCount: this.childCount !== null ? this.childCount : ''
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

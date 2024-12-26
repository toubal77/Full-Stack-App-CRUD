import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { Router } from '@angular/router';
import { Category } from '../core/models/Category';
import { Observable } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { CategoryService } from '../services/category.service';
import { SearchDialogComponent } from '../search-dialog/search-dialog.component';

@Component({
  selector: 'app-list-categories',
  templateUrl: './list-categories.component.html',
  styleUrls: ['./list-categories.component.scss']
})
export class ListCategoriesComponent implements OnInit, AfterViewInit {
  displayedColumns: string[] = ['Nom', 'Parent', 'Date_Creation', 'Enfant', 'Racine', 'actions'];
  dataSource = new MatTableDataSource<Category>([]);
  msgError: any;
  originalData: any[] = [];

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private router: Router,
    private categoryService: CategoryService,
    private snackBar: MatSnackBar,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.refresh();
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  filterData(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
    this.dataSource.filterPredicate = (data: any, filter: string) => {
      return data.name.toLowerCase().startsWith(filter);
    };
  }

  openSearchDialog() {
    const dialogRef = this.dialog.open(SearchDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.dataSource.data = result;
      }
    });
  }

  navigateToCreateCategory() {
    this.router.navigate(['/create-categorie']);
  }

  loadData(): Observable<Category[]> {
    return this.categoryService.getAllCategories();
  }

  refresh(): void {
    this.loadData().subscribe({
      next: (data) => {
        this.dataSource.data = data;
      },
      error: (error) => {
        console.error('Erreur lors du chargement des catégories: ', error);
        this.msgError = 'Erreur lors du chargement des catégories';
      }
    });
  }

  editCategory(row: Category): void {
    this.router.navigate(['/edit-categorie', row.id]);
  }

  deleteCategory(row: Category): void {
    const dialogRef = this.dialog.open(ConfirmDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const id = row.id;

        this.categoryService.deleteCategory(id).subscribe({
          next: (response) => {
            this.refresh();
            this.snackBar.open(response.message, 'Fermer', {
              duration: 4000,
              horizontalPosition: 'right',
              verticalPosition: 'top'
            });
          },
          error: (error) => {
            this.msgError = 'Erreur lors de la suppression de la catégorie';
            this.snackBar.open(this.msgError, 'Fermer', {
              duration: 3000,
              horizontalPosition: 'right',
              verticalPosition: 'top'
            });
          }
        });
      }
    });
  }
}

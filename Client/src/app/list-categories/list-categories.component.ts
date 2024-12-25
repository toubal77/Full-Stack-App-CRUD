import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';

@Component({
  selector: 'app-list-categories',
  templateUrl: './list-categories.component.html',
  styleUrls: ['./list-categories.component.scss']
})
export class ListCategoriesComponent implements OnInit, AfterViewInit {
  displayedColumns: string[] = ['Nom', 'Parent', 'Date_Creation', 'Enfant', 'Racine', 'actions'];
  dataSource = new MatTableDataSource<any>([]);
  msgError: any;
  originalData: any[] = [];
  isRootCategory: boolean = false;  // filtre pour les categories racines
  creationDateAfter: Date | null = null; // date apres
  creationDateBefore: Date | null = null; // date avant
  creationDateFrom: Date | null = null; // date de debut
  creationDateTo: Date | null = null; // date de fin
  childCount: number | null = null; // nombre de categories enfants
  filterValue: string = ""; // valeur de filtre par nom

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  ngOnInit(): void {
    this.loadData();
    this.dataSource.paginator = this.paginator;
    console.log(this.dataSource);
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  loadData(): void {
    this.originalData = [
      { Nom: "Categorie 1", Parent: " - ", Date_Creation: '06-09-2024', Enfant: 'Categorie 2', Racine: 'Oui' },
      { Nom: "Cat 2", Parent: "Categorie 1", Date_Creation: '06-10-2024', Enfant: ' - ', Racine: 'Non' },
      { Nom: "Categorie 3", Parent: " - ", Date_Creation: '06-01-2023', Enfant: ' - ', Racine: 'Oui' },
      { Nom: "Cat 4", Parent: " - ", Date_Creation: '06-10-2024', Enfant: 'Categorie 3', Racine: 'Oui' },
      { Nom: "Categorie 15", Parent: "Categorie 2", Date_Creation: '06-10-2024', Enfant: ' - ', Racine: 'Non' },
      { Nom: "Categ 12", Parent: " - ", Date_Creation: '14-02-2024', Enfant: ' - ', Racine: 'Oui' },
      { Nom: "Categorie 13", Parent: " - ", Date_Creation: '21-04-2024', Enfant: 'Categorie 12', Racine: 'Oui' },
      { Nom: "Categor 25", Parent: "Categorie 1", Date_Creation: '10-10-2024', Enfant: ' - ', Racine: 'Non' },
      { Nom: "Cat 7", Parent: " - ", Date_Creation: '06-10-2024', Enfant: ' - ', Racine: 'Oui' },
      { Nom: "Categor 9", Parent: " - ", Date_Creation: '06-10-2024', Enfant: 'Categorie', Racine: 'Oui' },
      { Nom: "Categori 22", Parent: "Categorie 9", Date_Creation: '06-10-2024', Enfant: ' - ', Racine: 'Non' },
      { Nom: "Catego 11", Parent: " - ", Date_Creation: '06-10-2020', Enfant: ' - ', Racine: 'Oui' },
    ];
   
    this.dataSource.data = this.originalData; 
  }

  filterData(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
    this.dataSource.filterPredicate = (data: any, filter: string) => {
      return data.Nom.toLowerCase().startsWith(filter);
    };
  }
  
  // filterData(): void {
  //   this.dataSource.filter = this.filterValue.trim().toLowerCase();

  //   const filteredData = this.dataSource.data.filter(category => {
  //     const matchesRoot = this.isRootCategory ? category.Racine === 'Oui' : true;
  //     const matchesDateAfter = this.creationDateAfter ? category.Date_Creation >= this.creationDateAfter : true;
  //     const matchesDateBefore = this.creationDateBefore ? category.Date_Creation <= this.creationDateBefore : true;
  //     const matchesDateRange = (this.creationDateFrom && this.creationDateTo) 
  //       ? category.Date_Creation >= this.creationDateFrom && category.Date_Creation <= this.creationDateTo 
  //       : true;
  //     const matchesChildCount = this.childCount !== null ? category.Enfant === this.childCount : true;

  //     return matchesRoot && matchesDateAfter && matchesDateBefore && matchesDateRange && matchesChildCount;
  //   });

  //   this.dataSource.data = filteredData;
  //   this.dataSource.paginator = this.paginator;
  // }

  editCategory(row: any[]): void {
    console.log('Edit', row);
  }

  deleteCategory(row: any[]): void {
    console.log('Delete', row);
  }
}
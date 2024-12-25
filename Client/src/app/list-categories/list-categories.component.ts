import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-list-categories',
  templateUrl: './list-categories.component.html',
  styleUrls: ['./list-categories.component.scss']
})
export class ListCategoriesComponent implements OnInit {
  displayedColumns: string[] = ['Nom', 'Parent', 'Date_Creation', 'Enfant', 'Racine', 'actions'];
  dataSource = new MatTableDataSource<any>([]);
  msgError: any;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  ngOnInit(): void {
    this.loadData();
    //this.dataSource.data =[]; // pour affiche la liste vide
    //this.msgError = "Message Erreur"; // pour affiche un message d'erreur
    this.dataSource.paginator = this.paginator;
    console.log(this.dataSource); 
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  loadData(): void {
    this.dataSource.data = [
      { Nom: "Categorie 1", Parent: " - ", Date_Creation: '06-10-2024', Enfant: 'Categorie 2', Racine: 'Oui' },
      { Nom: "Categorie 2", Parent: "Categorie 1", Date_Creation: '06-10-2024', Enfant: ' - ', Racine: 'Non' },
      { Nom: "Categorie 3", Parent: " - ", Date_Creation: '06-10-2024', Enfant: ' - ', Racine: 'Oui' },
      { Nom: "Categorie 1", Parent: " - ", Date_Creation: '06-10-2024', Enfant: 'Categorie 2', Racine: 'Oui' },
      { Nom: "Categorie 2", Parent: "Categorie 1", Date_Creation: '06-10-2024', Enfant: ' - ', Racine: 'Non' },
      { Nom: "Categorie 3", Parent: " - ", Date_Creation: '06-10-2024', Enfant: ' - ', Racine: 'Oui' },
      { Nom: "Categorie 1", Parent: " - ", Date_Creation: '06-10-2024', Enfant: 'Categorie 2', Racine: 'Oui' },
      { Nom: "Categorie 2", Parent: "Categorie 1", Date_Creation: '06-10-2024', Enfant: ' - ', Racine: 'Non' },
      { Nom: "Categorie 3", Parent: " - ", Date_Creation: '06-10-2024', Enfant: ' - ', Racine: 'Oui' },
      { Nom: "Categorie 1", Parent: " - ", Date_Creation: '06-10-2024', Enfant: 'Categorie 2', Racine: 'Oui' },
      { Nom: "Categorie 2", Parent: "Categorie 1", Date_Creation: '06-10-2024', Enfant: ' - ', Racine: 'Non' },
      { Nom: "Categorie 3", Parent: " - ", Date_Creation: '06-10-2024', Enfant: ' - ', Racine: 'Oui' },
    ];
   
  }

  editCategory(row: any[]): void {
    console.log('Edit', row);
  }

  deleteCategory(row: any[]): void {
    console.log('Delete', row);
  }
}
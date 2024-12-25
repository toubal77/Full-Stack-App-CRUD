import { Component, OnInit } from '@angular/core';
import { Category } from '../core/models/Category'; 
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CategoryService } from '../services/category.service';

@Component({
  selector: 'app-create-category',
  templateUrl: './create-category.component.html',
  styleUrls: ['./create-category.component.scss']
})
export class CreateCategoryComponent implements OnInit {
  isEditMode: boolean = false;
  categoryId: number | null = null;
  selectedChild: Category | null = null;
  
  notificationMessage: string = '';
  newCategory: Category = {
    id: 0,
    name: '',
    creationDate: new Date(),
    childrens: [],
    ifRacine: false,
    parent: null 
  };
  categories: Category[] = [];

  newChildName: string = '';
  toggleMode(edit: boolean): void {
    this.isEditMode = edit;
    if (!edit) {
        // reinitialise le formulaire 
        this.newCategory = {
            id: 0,
            name: '',
            creationDate: new Date(),
            childrens: [],
            ifRacine: false,
            parent: null 
        };
    }
}

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private categoryService: CategoryService,
    private snackBar: MatSnackBar,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.isEditMode = true;
        this.categoryId = +id;
        this.loadCategory(this.categoryId);
      }
    });
    this.loadData();
  }

  loadCategory(id: number): void {
    this.categoryService.getCategoryById(id).subscribe((category) => {
      this.newCategory = category;
      console.log(this.newCategory);
      console.log(this.newCategory.childrens);
    });
  }

  loadData(): void {
    this.categoryService.getAllCategories().subscribe({
      next: (data) => {
        this.categories = data;
        console.log(this.categories);
      },
      error: (error) => {
        //console.error('Erreur lors du chargement des catégories: ', error);
        this.snackBar.open('Erreur lors du chargement des catégories', 'Fermer', {
          duration: 3000,
          horizontalPosition: 'right', 
          verticalPosition: 'top'
        });
      }
    });
  }

  createCategory(): void {
    const categoryPayload: any = {
      name: this.newCategory.name,
      ifRacine: this.newCategory.ifRacine,
    };

    if (!this.newCategory.ifRacine && this.newCategory.parent) {
      categoryPayload.parent = { id: this.newCategory.parent.id };
    }
    
    this.categoryService.createCategory(categoryPayload).subscribe({
      next: (response) => {
        //console.log(response);
        this.notificationMessage = response.message;
        // reinitialise le formulaire
        this.newCategory = {
          id: 0,
          name: '',
          creationDate: new Date(),
          childrens: [],
          ifRacine: false,
          parent: null 
        };
        //load la liste des categories parent apres l'ajout d'une nouvelle categorie
        this.loadData();
      },
      error: (error) => {
        //console.error('Erreur lors de la création de la catégorie:', error);
        this.notificationMessage = error.error.message;
      }
    });
  }

  isChildDisabledOrParant(child: Category): boolean {
     // empeche l'utilisateur de selectionne le parent comme enfant
     if (child.id === this.newCategory.id) {
      return true; 
    }
    // empeche l'utilisateur de selectionne un enfant deja selectionne
    return this.newCategory.childrens.some(existingChild => existingChild.id === child.id);
  }

  isParent(parent: Category): boolean {
    // empeche l'utilisateur de selectionne lui meme comme parent
    return parent.id === this.newCategory.id;
  }

  editCategory(): void {
    const categoryId = this.newCategory.id;
    let categoryUpdate: any;
     if (this.newCategory.childrens && this.newCategory.childrens.length > 0) {
      categoryUpdate = {
        name: this.newCategory.name,
        ifRacine: this.newCategory.ifRacine,
      };
    } 
     if (this.newCategory.ifRacine) {
      categoryUpdate = {
        name: this.newCategory.name,
        ifRacine: true,
      };
    } 
     if (this.newCategory.parent != null && !this.newCategory.ifRacine) {
      categoryUpdate = {
        name: this.newCategory.name,
        parent: {
          id: this.newCategory.parent.id,
        },
        ifRacine: this.newCategory.ifRacine,
      };
    }
    console.log("send to update", categoryUpdate);
    console.log("newCategory", this.newCategory);
    this.categoryService.updateCategory(categoryId, categoryUpdate).subscribe({
      next: (response) => {
        this.snackBar.open(response.message, 'Fermer', {
          duration: 3000,
          horizontalPosition: 'right',
          verticalPosition: 'top',
        });
      },
      error: (error) => {
        this.snackBar.open(error.error.message, 'Fermer', {
          duration: 3000,
          horizontalPosition: 'right',
          verticalPosition: 'top',
        });
      }
    });
  }

  addChild(): void {
    if (this.selectedChild) {
      this.newCategory.childrens.push(this.selectedChild);

      const childrenIds = this.newCategory.childrens.map(child => child.id);
      const payload = {
        id: this.newCategory.id,
        childrens: childrenIds,
      };
      this.categoryService.updateCategoryChildren(payload)
        .subscribe({
          next: (response) => {
            this.snackBar.open(response.message, 'Fermer', {
              duration: 3000,
              horizontalPosition: 'right',
              verticalPosition: 'top'
            });
          },
          error: (error) => {
            this.snackBar.open(error.error.message, 'Fermer', {
              duration: 3000,
              horizontalPosition: 'right',
              verticalPosition: 'top'
            });
          }
        });
      // reinitialiser la selection
      this.selectedChild = null;
    } else {
      this.snackBar.open('Veuillez sélectionner un enfant', 'Fermer', {
        duration: 3000,
        horizontalPosition: 'right',
        verticalPosition: 'top'
      });
    }
  }


  deleteChild(child: Category): void {
    const index = this.newCategory.childrens.findIndex(
      existingChild => existingChild.id === child.id
    );
    if (index !== -1) {
      this.newCategory.childrens.splice(index, 1);
      
      const childrenIds = this.newCategory.childrens.map(child => child.id);
      const payload = {
        id: this.newCategory.id,
        childrens: childrenIds,
        childrensRemoved: [child.id]
      };
      this.categoryService.updateCategoryChildren(payload)
        .subscribe({
          next: (response) => {
            this.snackBar.open(response.message, 'Fermer', {
              duration: 3000,
              horizontalPosition: 'right',
              verticalPosition: 'top'
            });
          },
          error: (error) => {
            this.snackBar.open(error.error.message, 'Fermer', {
              duration: 3000,
              horizontalPosition: 'right',
              verticalPosition: 'top'
            });
          }
        });
    } else {
      this.snackBar.open('Enfant introuvable dans la liste', 'Fermer', {
        duration: 3000,
        horizontalPosition: 'right',
        verticalPosition: 'top'
      });
    }
  }

}

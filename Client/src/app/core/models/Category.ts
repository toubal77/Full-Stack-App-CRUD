export class Category {
  id: number;
  name: string;
  creationDate: Date;
  childrens: Category[];
  ifRacine: boolean;
  parent: Category | null;
  nbrChildrens: number;

  constructor(
    id: number,
    name: string,
    creationDate: Date,
    childrens: Category[],
    ifRacine: boolean,
    parent: Category | null,
    nbrChildrens: number
  ) {
    this.id = id;
    this.name = name;
    this.creationDate = creationDate;
    this.childrens = childrens;
    this.ifRacine = ifRacine;
    this.parent = parent;
    this.nbrChildrens = nbrChildrens;
  }
}

export class Category {
  id: number;
  name: string;
  creationDate: Date;
  childrens: Category[];
  ifRacine: boolean;
  parent: Category | null;

  constructor(
    id: number,
    name: string,
    creationDate: Date,
    childrens: Category[],
    ifRacine: boolean,
    parent: Category | null
  ) {
    this.id = id;
    this.name = name;
    this.creationDate = creationDate;
    this.childrens = childrens;
    this.ifRacine = ifRacine;
    this.parent = parent;
  }
}

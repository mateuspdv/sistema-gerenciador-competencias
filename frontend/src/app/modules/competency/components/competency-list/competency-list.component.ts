import { CategoryModel } from './../../models/category.model';
import { CategoryService } from './../../services/category.service';
import { CompetencyModel } from './../../models/competency.model';
import { CompetencyService } from './../../services/competency.service';
import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-competency-list',
  templateUrl: './competency-list.component.html',
  styleUrls: ['./competency-list.component.scss'],
  providers: [MessageService]
})
export class CompetencyListComponent implements OnInit {

  competencies: CompetencyModel[] = [];

  categories: CategoryModel[] = [];

  cols: any[] = [];

  constructor(private messageService: MessageService,
              private competencyService: CompetencyService,
              private categoryService: CategoryService) { }

  ngOnInit(): void {
    this.setColumns();
    this.findCompetencies();
    this.findCategories();
  }

  setColumns(): void {
    this.cols = [
        {field: 'name', header: 'Nome'},
        {field: 'description', header: 'Descrição'},
        {field: 'idCategory', header: 'Categoria'},
        {field: 'actions', header: 'Ações'},
    ];
  }

  addToast(severity: string, summary: string, detail: string): void {
    this.messageService.add({
        severity: severity,
        summary: summary,
        detail: detail,
        life: 4000
    });
  }

  findCompetencies(): void {
    this.competencyService.findAll().subscribe({
        next: (competencies) => {
            this.competencies = competencies;
        },
        error: (error) => {
            this.addToast('error', 'Erro ao carregar competência', error.message);
        }
    });
  }

  findCategories(): void {
    this.categoryService.findAll().subscribe({
        next: (categories) => {
            this.categories = categories;
        },
        error: (error) => {
            this.addToast('error', 'Erro ao carregar categorias', error.message);
        }
    });
  }

  getCategoryName(idCategory: number): any {
    const category = this.categories.find(category => category.id == idCategory);
    return category?.name;
  }

  isStringField(field: any): boolean {
    return field === 'name' || field === 'description';
  }

}

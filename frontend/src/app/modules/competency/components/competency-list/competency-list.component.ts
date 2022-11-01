import { CategoryModel } from './../../models/category.model';
import { CategoryService } from './../../services/category.service';
import { CompetencyModel } from './../../models/competency.model';
import { CompetencyService } from './../../services/competency.service';
import { Component, OnInit, EventEmitter } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';

@Component({
  selector: 'app-competency-list',
  templateUrl: './competency-list.component.html',
  styleUrls: ['./competency-list.component.scss'],
  providers: [MessageService, ConfirmationService]
})
export class CompetencyListComponent implements OnInit {

  competencies: CompetencyModel[] = [];

  categories: CategoryModel[] = [];

  cols: any[] = [];

  displayForm: boolean = false;

  constructor(private messageService: MessageService,
              private competencyService: CompetencyService,
              private categoryService: CategoryService,
              private confirmationService: ConfirmationService) { }

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

  deleteCompetency(idCompetency: number): void {
    this.competencyService.deleteById(idCompetency).subscribe({
        next: () => {
            this.addToast('success', '', 'Exclusão realizada com sucesso!');
            this.findCompetencies();
        },
        error: (error) => {
            this.addToast('error', 'Erro ao excluir competência', error.message);
        }
    })
  }

  getCategoryName(idCategory: number): any {
    const category = this.categories.find(category => category.id == idCategory);
    return category?.name;
  }

  isStringField(field: string): boolean {
    return field === 'name' || field === 'description';
  }

  confirmDeleteCompetency(competency: CompetencyModel) : void {
    this.confirmationService.confirm({
        message: 'Deseja realmente excluir a competência ' + competency.name + '?',
        accept: () => {
            this.deleteCompetency(competency.id);
        }
    });
  }

  openForm(): void {
    this.displayForm = true;
  }

  closeForm(): void {
    this.displayForm = false;
  }

}

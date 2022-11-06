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

  cols: any[] = [];

  displayForm: boolean = false;

  selectedCompetency!: CompetencyModel;

  action: string = 'create';

  constructor(private messageService: MessageService,
              private competencyService: CompetencyService,
              private confirmationService: ConfirmationService) { }

  ngOnInit(): void {
    this.setColumns();
    this.findCompetencies();
  }

  setColumns(): void {
    this.cols = [
        {field: 'name', header: 'Nome'},
        {field: 'description', header: 'Descrição'},
        {field: 'nameCategory', header: 'Categoria'},
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
            this.addToast('error', 'Erro ao carregar competências', error.message);
        }
    });
  }

  deleteCompetency(idCompetency: number): void {
    this.competencyService.deleteById(idCompetency).subscribe({
        next: () => {
            this.addToast('success', '', 'Exclusão realizada com sucesso!');
            this.refreshCompetencies();
        },
        error: (error) => {
            this.addToast('error', 'Erro ao excluir competência', error.message);
        }
    });
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

  refreshCompetencies(): void {
    this.findCompetencies();
  }

  defineCompetencyToUpdate(competency: CompetencyModel): void {
    this.action = 'update';
    this.selectedCompetency = competency;
    this.openForm();
  }

  createCompetency(): void {
    this.action = 'create';
    this.openForm();
  }

}

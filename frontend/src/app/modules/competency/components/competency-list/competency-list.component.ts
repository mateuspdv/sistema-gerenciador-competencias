import { CompetencyModel } from './../../models/competency.model';
import { CompetencyService } from './../../services/competency.service';
import { Component, OnInit, ViewChild} from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Paginator } from 'primeng/paginator';

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

  totalElements: number = 0;

  currentPage: number = 0;

  totalPages: number = 0;

  numberElementsPage: number = 0;

  isSearch: boolean = false;

  constructor(private messageService: MessageService,
              private competencyService: CompetencyService,
              private confirmationService: ConfirmationService) { }

  ngOnInit(): void {
    this.setColumns();
    this.findCompetencies(this.currentPage);
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

  findCompetencies(page: number): void {
    this.competencyService.findAll(page).subscribe({
        next: (data: any) => {
            this.competencies = data['content'];
            this.totalElements = data['totalElements'];
            this.numberElementsPage = data['numberOfElements'];
        },
        error: (error) => {
            this.addToast('error', 'Erro ao carregar competências', error.message);
        }
    });
  }

  globalSearchFilter(query: string): void {
    this.competencyService.globalSearchFilter(query).subscribe({
        next: (data: any) => {
            this.competencies = data['content'];
            this.totalElements = data['totalElements'];
            this.numberElementsPage = data['numberOfElements'];
        },
        error: (error) => {
            this.addToast('error', 'Erro ao realizar busca por competências', error.message);
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
    if(this.action == 'create' && this.numberElementsPage == 5) {
        this.findCompetencies(this.currentPage + 1);
        return;
    }
    this.findCompetencies(this.currentPage);
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

  paginate(event: any) {
    if(!this.isSearch) {
        this.currentPage = event.page;
        this.findCompetencies(this.currentPage);
    }
    this.isSearch = false;
  }

  search(event: any): void {
    this.isSearch = true;
    if(event.target.value == '' || event.target.value == undefined) {
        this.findCompetencies(0);
        return;
    }
    this.globalSearchFilter(event.target.value);
  }

}

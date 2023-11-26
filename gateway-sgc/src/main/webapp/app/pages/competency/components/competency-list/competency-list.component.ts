import { FormAction } from './../../../../shared/enums/form-action.enum';
import { CompetencyService } from './../../services/competency.service';
import { ViewCompetency } from './../../models/view-competency.model';
import { Component, OnInit } from '@angular/core';
import { Competency } from '../../models/competency.model';
import { ConfirmationService, MessageService } from 'primeng/api';

@Component({
  selector: 'competency-list',
  templateUrl: './competency-list.component.html',
  styleUrls: ['./competency-list.component.scss'],
  providers: [ConfirmationService, MessageService]
})
export class CompetencyListComponent implements OnInit {

  competencies: ViewCompetency[] = [];
  displayForm: boolean = false;
  formAction!: FormAction;
  selectedCompetency!: Competency;

  constructor(private competencyService: CompetencyService,
              private confirmationService: ConfirmationService,
              private messageService: MessageService) { }

  ngOnInit(): void {
    this.findAll();
  }

  findAll(): void {
    this.competencyService.findAll().subscribe({
      next: (res: ViewCompetency[]) => this.competencies = res
    });
  }

  createButton(): void {
    this.formAction = FormAction.CREATE;
    this.displayForm = true;
  }

  editButton(id: number): void {
    this.formAction = FormAction.EDIT;
    this.competencyService.findById(id).subscribe({
      next: (res: Competency) => {
        this.selectedCompetency = res;
        this.displayForm = true;
      }
    });
  }

  delete(id: number): void {
    this.competencyService.delete(id).subscribe({
      next: () => {
        this.messageService.add({ severity: 'success', detail: 'Competency successfully deleted!', life: 3000 });
        this.findAll();
      }
    });
  }

  deleteButton(id: number): void {
    this.confirmationService.confirm({
      message: 'Are you sure that you want to proceed?',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle',
      accept: () => this.delete(id)
    });
  }

}

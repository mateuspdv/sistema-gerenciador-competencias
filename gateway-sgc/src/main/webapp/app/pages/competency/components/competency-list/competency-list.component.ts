import { FormAction } from './../../../../shared/enums/form-action.enum';
import { CompetencyService } from './../../services/competency.service';
import { ViewCompetency } from './../../models/view-competency.model';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'competency-list',
  templateUrl: './competency-list.component.html',
  styleUrls: ['./competency-list.component.scss']
})
export class CompetencyListComponent implements OnInit {

  competencies: ViewCompetency[] = [];
  displayForm: boolean = false;
  formAction!: FormAction;

  constructor(private competencyService: CompetencyService) { }

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

}

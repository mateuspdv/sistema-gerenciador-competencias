import { CompetencyService } from './../../services/competency.service';
import { CompetencyModel } from './../../models/competency.model';
import { CategoryService } from './../../services/category.service';
import { CategoryModel } from './../../models/category.model';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SelectItem, MessageService } from 'primeng/api';

@Component({
  selector: 'app-competency-form',
  templateUrl: './competency-form.component.html',
  styleUrls: ['./competency-form.component.scss']
})
export class CompetencyFormComponent implements OnInit {

  formGroup!: FormGroup;

  dropdownCategories: SelectItem[] = [];

  @Input() showForm: boolean = false;

  @Input() competencyToUpdate!: CompetencyModel;

  @Input() typeAction: string = 'create';

  @Output() closeForm: EventEmitter<void> = new EventEmitter<void>();

  @Output() refreshCompetencies: EventEmitter<void> = new EventEmitter<void>();

  constructor(private categoryService: CategoryService,
              private formBuilder: FormBuilder,
              private competencyService: CompetencyService,
              private messageService: MessageService) { }

  ngOnInit(): void {
    this.buildFormGrup();
    this.findCategories();
    console.log(this.formGroup);
  }

  buildFormGrup(): void {
    this.formGroup = this.formBuilder.group({
        id: [null],
        name: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
        description: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
        idCategory: [null, [Validators.required]]
    });
  }

  addToast(severity: string, summary: string, detail: string): void {
    this.messageService.add({
        severity: severity,
        summary: summary,
        detail: detail,
        life: 4000
    });
  }

  close(): void {
    this.formGroup.reset();
    this.closeForm.emit();
  }

  findCategories(): void {
    this.categoryService.findAll().subscribe({
        next: (categories) => {
            this.convertCategoriesToDropdown(categories);
        },
        error: (error) => {
            this.addToast('error', 'Erro ao carregar categorias', error.message);
        }
    });
  }

  updateCompetency(competency: CompetencyModel) {
    this.competencyService.update(competency).subscribe({
        next: () => {
            this.addToast('success', '', 'Competência editada com sucesso');
            this.close();
            this.refreshCompetencies.emit();
        },
        error: (error) => {
            this.addToast('error', 'Erro ao cadastrar competência', error.message);
            this.close();
            this.refreshCompetencies.emit();
        }
    });
  }

  createCompetency(competency: CompetencyModel) {
    this.competencyService.create(competency).subscribe({
        next: () => {
            this.addToast('success','', 'Competência cadastrada com sucesso');
            this.close();
            this.refreshCompetencies.emit();
        },
        error: (error) => {
            console.log(error);
            this.addToast('error', 'Erro ao cadastrar competência', error.message);
        }
    });
  }

  convertCategoriesToDropdown(categories: CategoryModel[]) : void {
    categories.forEach(category => {
        this.dropdownCategories.push({ value: category.id, label: category.name});
    });
  }

  submit(): void {
    if(this.typeAction == 'create') {
        this.createCompetency(this.formGroup.value);
        return;
    }

    this.updateCompetency(this.formGroup.value);
  }

  selectedAction(): void {
    if(this.typeAction == 'update') {
        this.formGroup.patchValue(this.competencyToUpdate);
    }
  }

  getLabelButtonSubmit(): string {
    if(this.typeAction == 'create') {
        return 'Cadastrar';
    }
    return 'Editar';
  }

  getTitle(): string {
    if(this.typeAction == 'create') {
        return 'Cadastrar Competência';
    }
    return 'Editar Competência - ' + this.competencyToUpdate.name;
  }

}

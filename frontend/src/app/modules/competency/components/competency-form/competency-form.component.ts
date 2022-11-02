import { CompetencyService } from './../../services/competency.service';
import { CompetencyModel } from './../../models/competency.model';
import { ToastMessageModel } from '../../../../shared/models/toast-message.model';
import { CategoryService } from './../../services/category.service';
import { CategoryModel } from './../../models/category.model';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SelectItem } from 'primeng/api';

@Component({
  selector: 'app-competency-form',
  templateUrl: './competency-form.component.html',
  styleUrls: ['./competency-form.component.scss']
})
export class CompetencyFormComponent implements OnInit {

  formGroup!: FormGroup;

  dropdownCategories: SelectItem[] = [];

  @Input() showForm: boolean = false;

  @Output() closeForm: EventEmitter<void> = new EventEmitter<void>();

  @Output() sendToastMessage: EventEmitter<ToastMessageModel> = new EventEmitter<ToastMessageModel>();

  @Output() refreshCompetencies: EventEmitter<void> = new EventEmitter<void>();

  constructor(private categoryService: CategoryService,
              private formBuilder: FormBuilder,
              private competencyService: CompetencyService) { }

  ngOnInit(): void {
    this.buildFormGrup();
    this.findCategories();
  }

  buildFormGrup(): void {
    this.formGroup = this.formBuilder.group({
        name: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
        description: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
        idCategory: [null, [Validators.required]]
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
            const toastMessageModel = {
                severity: 'error',
                summary: 'Erro ao carregar categorias',
                detail: error.message
            }
            this.sendToastMessage.emit(toastMessageModel);
        }
    });
  }

  createCompetency(competency: CompetencyModel) {
    this.competencyService.create(competency).subscribe({
        next: () => {
            const toastMessageModel = {
                severity: 'success',
                summary: '',
                detail: 'Competência cadastrada com sucesso'
            }
            this.sendToastMessage.emit(toastMessageModel);
            this.close();
            this.refreshCompetencies.emit();
            this.formGroup.reset();
        },
        error: (error) => {
            const toastMessageModel = {
                severity: 'error',
                summary: 'Erro ao cadastrar competência',
                detail: error.message
            }
            this.sendToastMessage.emit(toastMessageModel);
        }
    });
  }

  convertCategoriesToDropdown(categories: CategoryModel[]) : void {
    categories.forEach(category => {
        this.dropdownCategories.push({ value: category.id, label: category.name});
    });
  }

  submit(): void {
    this.createCompetency(this.formGroup.value);
  }

}

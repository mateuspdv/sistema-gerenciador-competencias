import { CompetencyService } from './../../services/competency.service';
import { CategoryService } from './../../services/category.service';
import { FormAction } from './../../../../shared/enums/form-action.enum';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Dropdown } from '../../../../shared/models/dropdown.model';
import { Competency } from '../../models/competency.model';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'competency-form',
  templateUrl: './competency-form.component.html',
  styleUrls: ['./competency-form.component.scss'],
  providers: [MessageService]
})
export class CompetencyFormComponent implements OnInit{

  form!: FormGroup;
  optionsCategory: Dropdown[] = [];
  @Input() displayForm: boolean = false;
  @Input() formAction!: FormAction;
  @Input() selectedCompetency!: Competency;
  @Output() closedForm: EventEmitter<void> = new EventEmitter<void>();
  @Output() refreshList: EventEmitter<void> = new EventEmitter<void>();

  constructor(private formBuilder: FormBuilder,
              private categoryService: CategoryService,
              private competencyService: CompetencyService,
              private messageService: MessageService) { }

  ngOnInit(): void {
    this.buildForm();
    this.findCategoryOptions();
  }

  buildForm(): void {
    this.form = this.formBuilder.group(({
      id: [null],
      name: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
      description: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
      idCategory: [null, [Validators.required]]
    }));
  }

  hideForm(): void {
    this.displayForm = false;
    this.closedForm.emit();
  }

  showForm(): void {
    if(this.formAction == FormAction.CREATE) {
      this.form.reset();
      return;
    }

    if(this.formAction == FormAction.EDIT) {
      this.form.patchValue(this.selectedCompetency);
      return;
    }
  }

  persistForm(): void {
    if(this.formAction == FormAction.CREATE) {
      this.save();
      return;
    }

    if(this.formAction == FormAction.EDIT) {
      this.update();
      return;
    }
  }

  isFormInvalid(): boolean {
    return this.form.invalid;
  }

  findCategoryOptions(): void {
    this.categoryService.findAllDropdown().subscribe({
      next: (res: Dropdown[]) => this.optionsCategory = res
    });
  }

  save(): void {
    this.competencyService.save(this.form.value).subscribe({
      next: (res: Competency) => {
        this.hideForm();
        this.refreshList.emit();
        this.messageService.add({ severity: 'success', detail: 'Competency successfully registered!', life: 3000 });
      }
    });
  }

  update(): void {
    this.competencyService.update(this.form.value).subscribe({
      next: (res: Competency) => {
        this.hideForm();
        this.refreshList.emit();
        this.messageService.add({ severity: 'success', detail: 'Competency successfully edited!', life: 3000 });
      }
    });
  }


  getTitle(): string {
    if(this.formAction == FormAction.CREATE) {
      return 'Create Competency';
    }
    return 'Edit Competency';
  }

  getNamePersistButton(): string {
    if(this.formAction == FormAction.CREATE) {
      return 'Create';
    }
    return 'Edit';
  }

}

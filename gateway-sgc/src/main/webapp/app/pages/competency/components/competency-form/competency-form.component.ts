import { CompetencyService } from './../../services/competency.service';
import { CategoryService } from './../../services/category.service';
import { FormAction } from './../../../../shared/enums/form-action.enum';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Dropdown } from '../../../../shared/models/dropdown.model';
import { Competency } from '../../models/competency.model';

@Component({
  selector: 'competency-form',
  templateUrl: './competency-form.component.html',
  styleUrls: ['./competency-form.component.scss']
})
export class CompetencyFormComponent implements OnInit{

  form!: FormGroup;
  optionsCategory: Dropdown[] = [];
  @Input() displayForm: boolean = false;
  @Input() formAction!: FormAction;
  @Output() closedForm: EventEmitter<void> = new EventEmitter<void>();
  @Output() refreshList: EventEmitter<void> = new EventEmitter<void>();

  constructor(private formBuilder: FormBuilder,
              private categoryService: CategoryService,
              private competencyService: CompetencyService) { }

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
    }
  }

  persistForm(): void {
    if(this.formAction == FormAction.CREATE) {
      this.save();
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
      }
    });
  }

}

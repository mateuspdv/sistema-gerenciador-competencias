import { FormBuilder, FormGroup } from '@angular/forms';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'competency-form',
  templateUrl: './competency-form.component.html',
  styleUrls: ['./competency-form.component.scss']
})
export class CompetencyFormComponent implements OnInit{

  form!: FormGroup;
  optionsCategory: any = [];
  @Input() displayForm: boolean = false;
  @Output() closedForm: EventEmitter<void> = new EventEmitter<void>();

  constructor(private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.buildCategoryOptions();
    this.buildForm();
  }

  buildForm(): void {
    this.form = this.formBuilder.group(({
      id: [null],
      name: [null],
      description: [null],
      idCategory: [null]
    }));
  }

  hideForm(): void {
    this.displayForm = false;
    this.closedForm.emit();
  }

  persistForm(): void {
    console.log('Logic to persist');
    console.log(this.form.value);
    this.hideForm();
  }

  buildCategoryOptions(): void {
    this.optionsCategory = [
      {label: 'Category 1', value: 1},
      {label: 'Category 2', value: 2},
      {label: 'Category 3', value: 1},
    ];
  }

}

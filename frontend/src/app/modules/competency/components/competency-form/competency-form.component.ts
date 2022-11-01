import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-competency-form',
  templateUrl: './competency-form.component.html',
  styleUrls: ['./competency-form.component.scss']
})
export class CompetencyFormComponent implements OnInit {

  @Input() showForm: boolean = false;

  @Output() closeForm: EventEmitter<void> = new EventEmitter<void>();

  constructor() { }

  ngOnInit(): void {
  }

  close(): void {
    this.closeForm.emit();
  }

}

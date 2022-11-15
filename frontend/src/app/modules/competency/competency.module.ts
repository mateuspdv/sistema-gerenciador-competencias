import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CompetencyRoutingModule } from './competency-routing.module';
import { CompetencyListComponent } from './components/competency-list/competency-list.component';
import {ToastModule} from 'primeng/toast';
import {TableModule} from 'primeng/table';
import {ButtonModule} from 'primeng/button';
import {TooltipModule} from 'primeng/tooltip';
import {ConfirmDialogModule} from 'primeng/confirmdialog';
import { CompetencyFormComponent } from './components/competency-form/competency-form.component';
import {DialogModule} from 'primeng/dialog';
import { ReactiveFormsModule } from '@angular/forms';
import {DropdownModule} from 'primeng/dropdown';
import {InputTextModule} from 'primeng/inputtext';
import {PaginatorModule} from 'primeng/paginator';


@NgModule({
  declarations: [
    CompetencyListComponent,
    CompetencyFormComponent
  ],
  imports: [
    CommonModule,
    CompetencyRoutingModule,
    ToastModule,
    TableModule,
    ButtonModule,
    TooltipModule,
    ConfirmDialogModule,
    DialogModule,
    ReactiveFormsModule,
    DropdownModule,
    InputTextModule,
    PaginatorModule
  ]
})
export class CompetencyModule { }

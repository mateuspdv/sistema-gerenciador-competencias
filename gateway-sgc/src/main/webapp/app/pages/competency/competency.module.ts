import { TranslateModule } from '@ngx-translate/core';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA  } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CompetencyRoutingModule } from './competency-routing.module';
import { CompetencyListComponent } from './components/competency-list/competency-list.component';

import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { DialogModule } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import { CompetencyFormComponent } from './components/competency-form/competency-form.component';
import { DropdownModule } from 'primeng/dropdown';
import { MessagesModule } from 'primeng/messages';
import { MessageModule } from 'primeng/message';
import { ToastModule } from 'primeng/toast';
import { ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    CompetencyListComponent,
    CompetencyFormComponent
  ],
  imports: [
    CommonModule,
    CompetencyRoutingModule,
    ReactiveFormsModule,
    ButtonModule,
    TableModule,
    DialogModule,
    InputTextModule,
    DropdownModule,
    MessagesModule,
    MessageModule,
    ToastModule,
    TranslateModule
  ],
  schemas: [
    CUSTOM_ELEMENTS_SCHEMA
  ]
})
export class CompetencyModule { }

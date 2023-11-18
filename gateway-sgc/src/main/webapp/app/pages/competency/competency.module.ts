import { NgModule, CUSTOM_ELEMENTS_SCHEMA  } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CompetencyRoutingModule } from './competency-routing.module';
import { CompetencyListComponent } from './components/competency-list/competency-list.component';

import { ButtonModule } from 'primeng/button';


@NgModule({
  declarations: [
    CompetencyListComponent
  ],
  imports: [
    CommonModule,
    CompetencyRoutingModule,
    ButtonModule
  ],
  schemas: [
    CUSTOM_ELEMENTS_SCHEMA
  ]
})
export class CompetencyModule { }

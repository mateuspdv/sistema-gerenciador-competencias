import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CompetencyRoutingModule } from './competency-routing.module';
import { CompetencyListComponent } from './components/competency-list/competency-list.component';


@NgModule({
  declarations: [
    CompetencyListComponent
  ],
  imports: [
    CommonModule,
    CompetencyRoutingModule
  ]
})
export class CompetencyModule { }

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CompetencyRoutingModule } from './competency-routing.module';
import { CompetencyListComponent } from './components/competency-list/competency-list.component';
import {ToastModule} from 'primeng/toast';
import {TableModule} from 'primeng/table';
import {ButtonModule} from 'primeng/button';
import {TooltipModule} from 'primeng/tooltip';


@NgModule({
  declarations: [
    CompetencyListComponent
  ],
  imports: [
    CommonModule,
    CompetencyRoutingModule,
    ToastModule,
    TableModule,
    ButtonModule,
    TooltipModule
  ]
})
export class CompetencyModule { }

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ContributorRoutingModule } from './contributor-routing.module';
import { ContributorListComponent } from './components/contributor-list/contributor-list.component';
import { ContributorFormComponent } from './components/contributor-form/contributor-form.component';
import { ContributorViewComponent } from './components/contributor-view/contributor-view.component';
import { TableModule } from 'primeng/table';


@NgModule({
  declarations: [
    ContributorListComponent,
    ContributorFormComponent,
    ContributorViewComponent
  ],
  imports: [
    CommonModule,
    ContributorRoutingModule,
    TableModule
  ]
})
export class ContributorModule { }

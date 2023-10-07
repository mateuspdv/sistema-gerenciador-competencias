import { SharedModule } from './../../shared/shared.module';
import { NgModule } from '@angular/core';

import { ContributorRoutingModule } from './contributor-routing.module';
import { ContributorListComponent } from './components/contributor-list/contributor-list.component';
import { ContributorFormComponent } from './components/contributor-form/contributor-form.component';
import { ContributorViewComponent } from './components/contributor-view/contributor-view.component';


@NgModule({
    declarations: [
        ContributorListComponent,
        ContributorFormComponent,
        ContributorViewComponent
    ],
    imports: [
        ContributorRoutingModule,
        SharedModule
    ]
})
export class ContributorModule { }

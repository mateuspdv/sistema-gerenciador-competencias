import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ContributorListComponent } from './components/contributor-list/contributor-list.component';

const routes: Routes = [{
    path: '', component: ContributorListComponent
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ContributorRoutingModule { }

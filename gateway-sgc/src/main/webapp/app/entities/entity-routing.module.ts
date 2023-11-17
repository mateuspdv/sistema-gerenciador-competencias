import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'competency',
        data: { pageTitle: 'gatewaySgcApp.coreSgcCompetency.home.title' },
        loadChildren: () => import('./core-sgc/competency/competency.routes'),
      },
      {
        path: 'category',
        data: { pageTitle: 'gatewaySgcApp.coreSgcCategory.home.title' },
        loadChildren: () => import('./core-sgc/category/category.routes'),
      },
      {
        path: 'seniority',
        data: { pageTitle: 'gatewaySgcApp.coreSgcSeniority.home.title' },
        loadChildren: () => import('./core-sgc/seniority/seniority.routes'),
      },
      {
        path: 'skill',
        data: { pageTitle: 'gatewaySgcApp.coreSgcSkill.home.title' },
        loadChildren: () => import('./core-sgc/skill/skill.routes'),
      },
      {
        path: 'contributor',
        data: { pageTitle: 'gatewaySgcApp.coreSgcContributor.home.title' },
        loadChildren: () => import('./core-sgc/contributor/contributor.routes'),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}

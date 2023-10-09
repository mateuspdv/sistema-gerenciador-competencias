import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CompetencyComponent } from './list/competency.component';
import { CompetencyDetailComponent } from './detail/competency-detail.component';
import { CompetencyUpdateComponent } from './update/competency-update.component';
import CompetencyResolve from './route/competency-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const competencyRoute: Routes = [
  {
    path: '',
    component: CompetencyComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CompetencyDetailComponent,
    resolve: {
      competency: CompetencyResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CompetencyUpdateComponent,
    resolve: {
      competency: CompetencyResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CompetencyUpdateComponent,
    resolve: {
      competency: CompetencyResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default competencyRoute;

import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ContributorComponent } from './list/contributor.component';
import { ContributorDetailComponent } from './detail/contributor-detail.component';
import { ContributorUpdateComponent } from './update/contributor-update.component';
import ContributorResolve from './route/contributor-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const contributorRoute: Routes = [
  {
    path: '',
    component: ContributorComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ContributorDetailComponent,
    resolve: {
      contributor: ContributorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ContributorUpdateComponent,
    resolve: {
      contributor: ContributorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ContributorUpdateComponent,
    resolve: {
      contributor: ContributorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default contributorRoute;

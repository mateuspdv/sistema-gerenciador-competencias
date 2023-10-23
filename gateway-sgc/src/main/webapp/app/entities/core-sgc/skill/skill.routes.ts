import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SkillComponent } from './list/skill.component';
import { SkillDetailComponent } from './detail/skill-detail.component';
import SkillResolve from './route/skill-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const skillRoute: Routes = [
  {
    path: '',
    component: SkillComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SkillDetailComponent,
    resolve: {
      skill: SkillResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default skillRoute;

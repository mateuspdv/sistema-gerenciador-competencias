import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICompetency } from '../competency.model';
import { CompetencyService } from '../service/competency.service';

export const competencyResolve = (route: ActivatedRouteSnapshot): Observable<null | ICompetency> => {
  const id = route.params['id'];
  if (id) {
    return inject(CompetencyService)
      .find(id)
      .pipe(
        mergeMap((competency: HttpResponse<ICompetency>) => {
          if (competency.body) {
            return of(competency.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        })
      );
  }
  return of(null);
};

export default competencyResolve;

import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IContributor } from '../contributor.model';
import { ContributorService } from '../service/contributor.service';

export const contributorResolve = (route: ActivatedRouteSnapshot): Observable<null | IContributor> => {
  const id = route.params['id'];
  if (id) {
    return inject(ContributorService)
      .find(id)
      .pipe(
        mergeMap((contributor: HttpResponse<IContributor>) => {
          if (contributor.body) {
            return of(contributor.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        })
      );
  }
  return of(null);
};

export default contributorResolve;

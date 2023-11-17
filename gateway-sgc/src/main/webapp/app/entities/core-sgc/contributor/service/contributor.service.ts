import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IContributor, NewContributor } from '../contributor.model';

export type PartialUpdateContributor = Partial<IContributor> & Pick<IContributor, 'id'>;

type RestOf<T extends IContributor | NewContributor> = Omit<T, 'birthDate' | 'admissionDate' | 'creationDate' | 'lastUpdateDate'> & {
  birthDate?: string | null;
  admissionDate?: string | null;
  creationDate?: string | null;
  lastUpdateDate?: string | null;
};

export type RestContributor = RestOf<IContributor>;

export type NewRestContributor = RestOf<NewContributor>;

export type PartialUpdateRestContributor = RestOf<PartialUpdateContributor>;

export type EntityResponseType = HttpResponse<IContributor>;
export type EntityArrayResponseType = HttpResponse<IContributor[]>;

@Injectable({ providedIn: 'root' })
export class ContributorService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/contributors', 'core-sgc');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(contributor: NewContributor): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contributor);
    return this.http
      .post<RestContributor>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(contributor: IContributor): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contributor);
    return this.http
      .put<RestContributor>(`${this.resourceUrl}/${this.getContributorIdentifier(contributor)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(contributor: PartialUpdateContributor): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contributor);
    return this.http
      .patch<RestContributor>(`${this.resourceUrl}/${this.getContributorIdentifier(contributor)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestContributor>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestContributor[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getContributorIdentifier(contributor: Pick<IContributor, 'id'>): number {
    return contributor.id;
  }

  compareContributor(o1: Pick<IContributor, 'id'> | null, o2: Pick<IContributor, 'id'> | null): boolean {
    return o1 && o2 ? this.getContributorIdentifier(o1) === this.getContributorIdentifier(o2) : o1 === o2;
  }

  addContributorToCollectionIfMissing<Type extends Pick<IContributor, 'id'>>(
    contributorCollection: Type[],
    ...contributorsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const contributors: Type[] = contributorsToCheck.filter(isPresent);
    if (contributors.length > 0) {
      const contributorCollectionIdentifiers = contributorCollection.map(
        contributorItem => this.getContributorIdentifier(contributorItem)!
      );
      const contributorsToAdd = contributors.filter(contributorItem => {
        const contributorIdentifier = this.getContributorIdentifier(contributorItem);
        if (contributorCollectionIdentifiers.includes(contributorIdentifier)) {
          return false;
        }
        contributorCollectionIdentifiers.push(contributorIdentifier);
        return true;
      });
      return [...contributorsToAdd, ...contributorCollection];
    }
    return contributorCollection;
  }

  protected convertDateFromClient<T extends IContributor | NewContributor | PartialUpdateContributor>(contributor: T): RestOf<T> {
    return {
      ...contributor,
      birthDate: contributor.birthDate?.format(DATE_FORMAT) ?? null,
      admissionDate: contributor.admissionDate?.format(DATE_FORMAT) ?? null,
      creationDate: contributor.creationDate?.toJSON() ?? null,
      lastUpdateDate: contributor.lastUpdateDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restContributor: RestContributor): IContributor {
    return {
      ...restContributor,
      birthDate: restContributor.birthDate ? dayjs(restContributor.birthDate) : undefined,
      admissionDate: restContributor.admissionDate ? dayjs(restContributor.admissionDate) : undefined,
      creationDate: restContributor.creationDate ? dayjs(restContributor.creationDate) : undefined,
      lastUpdateDate: restContributor.lastUpdateDate ? dayjs(restContributor.lastUpdateDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestContributor>): HttpResponse<IContributor> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestContributor[]>): HttpResponse<IContributor[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICompetency, NewCompetency } from '../competency.model';

export type PartialUpdateCompetency = Partial<ICompetency> & Pick<ICompetency, 'id'>;

type RestOf<T extends ICompetency | NewCompetency> = Omit<T, 'creationDate' | 'lastUpdateDate'> & {
  creationDate?: string | null;
  lastUpdateDate?: string | null;
};

export type RestCompetency = RestOf<ICompetency>;

export type NewRestCompetency = RestOf<NewCompetency>;

export type PartialUpdateRestCompetency = RestOf<PartialUpdateCompetency>;

export type EntityResponseType = HttpResponse<ICompetency>;
export type EntityArrayResponseType = HttpResponse<ICompetency[]>;

@Injectable({ providedIn: 'root' })
export class CompetencyService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/competencies', 'core-sgc');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(competency: NewCompetency): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(competency);
    return this.http
      .post<RestCompetency>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(competency: ICompetency): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(competency);
    return this.http
      .put<RestCompetency>(`${this.resourceUrl}/${this.getCompetencyIdentifier(competency)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(competency: PartialUpdateCompetency): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(competency);
    return this.http
      .patch<RestCompetency>(`${this.resourceUrl}/${this.getCompetencyIdentifier(competency)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestCompetency>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCompetency[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCompetencyIdentifier(competency: Pick<ICompetency, 'id'>): number {
    return competency.id;
  }

  compareCompetency(o1: Pick<ICompetency, 'id'> | null, o2: Pick<ICompetency, 'id'> | null): boolean {
    return o1 && o2 ? this.getCompetencyIdentifier(o1) === this.getCompetencyIdentifier(o2) : o1 === o2;
  }

  addCompetencyToCollectionIfMissing<Type extends Pick<ICompetency, 'id'>>(
    competencyCollection: Type[],
    ...competenciesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const competencies: Type[] = competenciesToCheck.filter(isPresent);
    if (competencies.length > 0) {
      const competencyCollectionIdentifiers = competencyCollection.map(competencyItem => this.getCompetencyIdentifier(competencyItem)!);
      const competenciesToAdd = competencies.filter(competencyItem => {
        const competencyIdentifier = this.getCompetencyIdentifier(competencyItem);
        if (competencyCollectionIdentifiers.includes(competencyIdentifier)) {
          return false;
        }
        competencyCollectionIdentifiers.push(competencyIdentifier);
        return true;
      });
      return [...competenciesToAdd, ...competencyCollection];
    }
    return competencyCollection;
  }

  protected convertDateFromClient<T extends ICompetency | NewCompetency | PartialUpdateCompetency>(competency: T): RestOf<T> {
    return {
      ...competency,
      creationDate: competency.creationDate?.format(DATE_FORMAT) ?? null,
      lastUpdateDate: competency.lastUpdateDate?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restCompetency: RestCompetency): ICompetency {
    return {
      ...restCompetency,
      creationDate: restCompetency.creationDate ? dayjs(restCompetency.creationDate) : undefined,
      lastUpdateDate: restCompetency.lastUpdateDate ? dayjs(restCompetency.lastUpdateDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestCompetency>): HttpResponse<ICompetency> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestCompetency[]>): HttpResponse<ICompetency[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}

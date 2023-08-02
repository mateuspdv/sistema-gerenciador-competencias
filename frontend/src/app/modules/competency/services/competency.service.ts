import { DropdownModel } from './../../../shared/models/dropdown.model';
import { CompetencyModel } from './../models/competency.model';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CompetencyFilterModel } from '../models/competency-filter.model';

@Injectable({
  providedIn: 'root'
})
export class CompetencyService {

  constructor(private httpClient: HttpClient) { }

  url: string = 'http://localhost:8080/api/competencia';

  findAll(page: number): Observable<CompetencyModel[]> {
    return this.httpClient.get<CompetencyModel[]>(this.url + '?page=' + page);
  }

  findAllDropDown(): Observable<DropdownModel[]> {
    return this.httpClient.get<DropdownModel[]>(this.url + `/competencias-dropdown`);
  }

  globalSearchFilter(query: string, page: number): Observable<CompetencyModel[]> {
    return this.httpClient.get<CompetencyModel[]>(this.url + '/filtro/' + query + '?page=' + page);
  }

  findById(idCompetency: number): Observable<CompetencyModel> {
    return this.httpClient.get<CompetencyModel>(this.url + '/' + idCompetency);
  }

  create(competencyModel: CompetencyModel): Observable<CompetencyModel> {
    return this.httpClient.post<CompetencyModel>(this.url, competencyModel);
  }

  update(competencyModel: CompetencyModel): Observable<CompetencyModel> {
    return this.httpClient.put<CompetencyModel>(this.url, competencyModel);
  }

  deleteById(idCompetency: number): Observable<void> {
    return this.httpClient.delete<void>(this.url + '/' + idCompetency);
  }

  columnsFilter(filter: CompetencyFilterModel, size: number, page: number): Observable<CompetencyModel> {
    const params = { page: page, size: size };
    return this.httpClient.post<CompetencyModel>(this.url + '/filter', filter, { params });
  }

}

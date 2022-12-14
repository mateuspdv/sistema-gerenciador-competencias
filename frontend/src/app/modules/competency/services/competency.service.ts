import { CompetencyModel } from './../models/competency.model';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CompetencyService {

  constructor(private httpClient: HttpClient) { }

  url: string = 'http://localhost:8080/api/competencia';

  findAll(page: number): Observable<CompetencyModel[]> {
    return this.httpClient.get<CompetencyModel[]>(this.url + '?page=' + page);
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

}

import { Competency } from './../models/competency.model';
import { ViewCompetency } from './../models/view-competency.model';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CompetencyService {

  private resourceUrl = this.applicationConfigService.getEndpointFor('api/competency', 'core-sgc');

  constructor(private httpClient: HttpClient,
              private applicationConfigService: ApplicationConfigService) { }

  findAll(): Observable<ViewCompetency[]> {
    return this.httpClient.get<ViewCompetency[]>(this.resourceUrl);
  }

  findById(id: number): Observable<Competency> {
    return this.httpClient.get<Competency>(`${this.resourceUrl}/${id}`);
  }

  save(competency: Competency): Observable<Competency> {
    return this.httpClient.post<Competency>(this.resourceUrl, competency);
  }

  update(competency: Competency): Observable<Competency> {
    return this.httpClient.put<Competency>(this.resourceUrl, competency);
  }

  delete(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.resourceUrl}/${id}`);
  }

}

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

}

import { DropdownModel } from './../../../shared/models/dropdown.model';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LevelCompetencyService {

  url: string = 'http://localhost:8080/api/nivel-competencia';

  constructor(private httpClient: HttpClient) { }

  findAll(): Observable<DropdownModel[]> {
    return this.httpClient.get<DropdownModel[]>(this.url);
  }

}

import { Dropdown } from './../../../shared/models/dropdown.model';
import { ApplicationConfigService } from './../../../core/config/application-config.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private resourceUrl = this.applicationConfigService.getEndpointFor('api/category', 'core-sgc');

  constructor(private httpClient: HttpClient,
              private applicationConfigService: ApplicationConfigService) { }

  findAllDropdown(): Observable<Dropdown[]> {
    return this.httpClient.get<Dropdown[]>(this.resourceUrl);
  }

}

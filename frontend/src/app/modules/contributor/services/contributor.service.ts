import { ContributorModel } from './../models/contributor.model';
import { ViewContributorModel } from './../models/view-contributor.model';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Page } from 'src/app/shared/models/page.model';

@Injectable({
    providedIn: 'root'
})
export class ContributorService {

    url: string = 'http://localhost:8080/api/colaborador';

    constructor(private httpClient: HttpClient) { }

    findAll(page: number): Observable<Page<ViewContributorModel>> {
        return this.httpClient.get<Page<ViewContributorModel>>(this.url + `?page=${page}`);
    }

    create(contributor: ContributorModel): Observable<ContributorModel> {
        return this.httpClient.post<ContributorModel>(this.url, contributor);
    }

}

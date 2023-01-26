import { Page } from './../../../../shared/models/page.model';
import { Component, OnInit } from '@angular/core';
import { ContributorService } from '../../services/contributor.service';
import { ViewContributorModel } from '../../models/view-contributor.model';

@Component({
    selector: 'app-contributor-list',
    templateUrl: './contributor-list.component.html',
    styleUrls: ['./contributor-list.component.scss']
})
export class ContributorListComponent implements OnInit {

    page: Page<ViewContributorModel> = new Page();

    cols: any[] = [];

    constructor(private contributorService: ContributorService) { }

    ngOnInit(): void {
        this.setColumns();
        this.findAll(this.page.number);
    }

    findAll(page: number): void {
        this.contributorService.findAll(page)
            .subscribe((data: Page<ViewContributorModel>) => {
                this.page = data;
            })
    }

    setColumns(): void {
        this.cols = [
            { header: 'Primeiro Nome', field: 'firstName' },
            { header: 'Último Nome', field: 'lastName' },
            { header: 'Data de Nascimento', field: 'birthDate' },
            { header: 'Senioridade', field: 'nameSeniority' },
        ]
    }

}

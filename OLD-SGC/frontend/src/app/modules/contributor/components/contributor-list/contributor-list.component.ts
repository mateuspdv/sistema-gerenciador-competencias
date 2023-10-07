import { ContributorModel } from './../../models/contributor.model';
import { Page } from './../../../../shared/models/page.model';
import { Component, OnInit } from '@angular/core';
import { ContributorService } from '../../services/contributor.service';
import { ViewContributorModel } from '../../models/view-contributor.model';
import { ConfirmationService, MessageService } from 'primeng/api';

@Component({
    selector: 'app-contributor-list',
    templateUrl: './contributor-list.component.html',
    styleUrls: ['./contributor-list.component.scss'],
    providers: [MessageService, ConfirmationService]
})
export class ContributorListComponent implements OnInit {

    page: Page<ViewContributorModel> = new Page();

    selectedContributor!: ContributorModel;

    showForm: boolean = false;

    editing: boolean = false;

    cols: any[] = [];

    constructor(private contributorService: ContributorService,
        private messageService: MessageService,
        private confirmationService: ConfirmationService) { }

    ngOnInit(): void {
        this.setColumns();
        this.findAll(this.page.number);
    }

    findAll(page: number): void {
        this.contributorService.findAll(page)
            .subscribe((data: Page<ViewContributorModel>) => {
                this.page = data;
            });
    }

    findById(idContributor: number): void {
        this.contributorService.findById(idContributor)
            .subscribe({
                next: (contributor: ContributorModel) => {
                    console.log(contributor);
                    this.selectedContributor = contributor;
                },
                error: (error) => {
                    console.log(error);
                }
            })
    }

    deleteById(idContributor: number): void {
        this.contributorService.deleteById(idContributor)
            .subscribe({
                next: () => {
                    this.addMessage('success', 'Colaborador excluído com sucesso!', 4000);
                    this.refreshData();
                },
                error: (error) => {
                    console.log(error);
                }
            })
    }

    addMessage(severity: string, detail: string, life: number): void {
        this.messageService.add({
            severity: severity,
            detail: detail,
            life: life
        });
    }

    setColumns(): void {
        this.cols = [
            { header: 'Nome', field: 'name' },
            { header: 'Data de Nascimento', field: 'birthDate' },
            { header: 'Senioridade', field: 'nameSeniority' },
            { header: 'Ações', field: 'actions' },
        ]
    }

    getFullName(contributor: ViewContributorModel): string {
        return contributor.firstName + ' ' + contributor.lastName;
    }

    update(idContributor: number): void {
        this.findById(idContributor);
        this.editing = true;
        this.showForm = true;
    }

    view(): void {

    }

    delete(contributor: ViewContributorModel): void {
        this.confirmationService.confirm({
            message: `Deseja realmente excluir o Colaborador ${contributor.firstName} ${contributor.lastName}?`,
            accept: () => {
                this.deleteById(contributor.id);
            }
        });
    }

    refreshData(): void {
        this.findAll(this.page.number);
    }

    closeForm(): void {
        this.showForm = false;
        this.editing = false;
        this.refreshData();
    }

    openForm(): void {
        this.editing = false;
        this.showForm = true;
    }

}

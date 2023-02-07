import { ContributorCompetencyModel } from './../../models/contributor-competency.model';
import { CompetencyService } from './../../../competency/services/competency.service';
import { LevelCompetencyService } from './../../services/level-competency.service';
import { ContributorModel } from './../../models/contributor.model';
import { DropdownModel } from './../../../../shared/models/dropdown.model';
import { SeniorityService } from './../../services/seniority.service';
import { ContributorService } from './../../services/contributor.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { SelectItem, MessageService } from 'primeng/api';

@Component({
    selector: 'app-contributor-form',
    templateUrl: './contributor-form.component.html',
    styleUrls: ['./contributor-form.component.scss']
})
export class ContributorFormComponent implements OnInit {

    formGroup!: FormGroup;

    dropdownSeniorities: SelectItem[] = [];

    dropdownCompetencies: SelectItem[] = [];

    dropdownLevelCompetency: SelectItem[] = [];

    idCompetencySelected!: number;

    idLevelCompetencySelected!: number;

    competenciesSelected: ContributorCompetencyModel[] = [];

    @Input() showForm: boolean = false;

    @Input() contributorToUpdate!: ContributorModel;

    @Input() editing: boolean = false;

    @Output() closeForm: EventEmitter<void> = new EventEmitter<void>();

    constructor(private formBuilder: FormBuilder,
        private contributorService: ContributorService,
        private seniorityService: SeniorityService,
        private messageService: MessageService,
        private levelCompetencyService: LevelCompetencyService,
        private competencyService: CompetencyService) { }

    ngOnInit(): void {
        this.buildFormGrup();
        this.findSeniorities();
        this.findLevelCompetency();
        this.findCompetenciesDropdown();
    }

    buildFormGrup(): void {
        this.formGroup = this.formBuilder.group({
            id: [null],
            firstName: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
            lastName: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
            cpf: [null, [Validators.required]],
            email: [null, [Validators.required]],
            birthDate: [null, [Validators.required]],
            admissionDate: [null, [Validators.required]],
            idSeniority: [null, [Validators.required]],
            competencies: [[]]
        });
    }

    findSeniorities(): void {
        this.seniorityService.findAll()
            .subscribe((data: DropdownModel[]) => {
                data.forEach(item => this.dropdownSeniorities.push({ value: item.id, label: item.name }))
            })
    }

    findLevelCompetency(): void {
        this.levelCompetencyService.findAll()
            .subscribe((data: DropdownModel[]) => {
                data.forEach(item => this.dropdownLevelCompetency.push({ value: item.id, label: item.name }))
            })
    }

    findCompetenciesDropdown(): void {
        this.competencyService.findAllDropDown()
            .subscribe((data: DropdownModel[]) => {
                data.forEach(item => this.dropdownCompetencies.push({ value: item.id, label: item.name }))
            })
    }

    createContributor(contributor: ContributorModel): void {
        this.contributorService.create(contributor)
            .subscribe({
                next: () => {
                    this.addMessage('success', 'Colaborador cadastrado com sucesso!', 4000);
                    this.close();
                },
                error: (error) => {
                    console.log(error);
                }
            })
    }

    updateContributor(contributor: ContributorModel): void {
        this.contributorService.update(contributor)
            .subscribe({
                next: () => {
                    this.addMessage('success', 'Colaborador editado com sucesso!', 4000);
                    this.close();
                },
                error: (error) => {
                    console.log(error);
                }
            })
    }

    checkCompetencyAlreadyAdded(contributorCompetencyModel: ContributorCompetencyModel): void {
        if(this.competenciesSelected.filter(competency => competency.idCompetency === contributorCompetencyModel.idCompetency).length > 0) {
            this.addMessage('error', 'Colaborador já leciona essa competência!', 3000);
            return;
        }
        this.competenciesSelected.push(contributorCompetencyModel);
    }

    addCompetency(): void {
        let contributorCompetencyModel: ContributorCompetencyModel = new ContributorCompetencyModel();
        contributorCompetencyModel.idCompetency = this.idCompetencySelected;
        contributorCompetencyModel.nameCompetency = this.dropdownCompetencies.find(competency => competency.value === this.idCompetencySelected)?.label;
        contributorCompetencyModel.idLevelCompetency = this.idLevelCompetencySelected;
        contributorCompetencyModel.nameLevelCompetency = this.dropdownLevelCompetency.find(levelCompetency => levelCompetency.value === this.idLevelCompetencySelected)?.label;
        this.checkCompetencyAlreadyAdded(contributorCompetencyModel);
    }

    excludeCompetency(contributorCompetencyModel: ContributorCompetencyModel) {
        const index: number = this.competenciesSelected.findIndex(competency => competency.idCompetency === contributorCompetencyModel.idCompetency);
        this.competenciesSelected.splice(index, 1);
        this.addMessage('success', 'Competência removida com sucesso!', 4000);
    }

    addMessage(severity: string, detail: string, life: number): void {
        this.messageService.add({
            severity: severity,
            detail: detail,
            life: life
        });
    }

    close(): void {
        this.showForm = false;
        this.editing = false;
        this.formGroup.reset();
        this.closeForm.emit();
    }

    isEditing(): void {
        if (this.editing) {
            this.contributorToUpdate.birthDate = new Date(this.contributorToUpdate.birthDate + 'T00:00');
            this.contributorToUpdate.admissionDate = new Date(this.contributorToUpdate.admissionDate + 'T00:00');
            this.competenciesSelected = this.contributorToUpdate.competencies;
            this.formGroup.patchValue(this.contributorToUpdate);
            return;
        }
        this.competenciesSelected = [];
        this.formGroup.get('idSeniority')?.setValue(this.dropdownSeniorities[1].value);
    }

    getTitle(): string {
        return this.editing ? 'Editar Colaborador' : 'Cadastrar Colaborador';
    }

    getLabelButtonSubmit(): string {
        return this.editing ? 'Editar' : 'Cadastrar';
    }

    submitForm(): void {
        this.formGroup.get('competencies')?.setValue(this.competenciesSelected);
        if (this.editing) {
            this.updateContributor(this.formGroup.value);
            return;
        }
        this.createContributor(this.formGroup.value);
    }

}

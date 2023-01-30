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

    @Input() showForm: boolean = false;

    @Input() contributorToUpdate!: ContributorModel;

    @Input() editing: boolean = false;

    @Output() closeForm: EventEmitter<void> = new EventEmitter<void>();

    constructor(private formBuilder: FormBuilder,
        private contributorService: ContributorService,
        private seniorityService: SeniorityService,
        private messageService: MessageService) { }

    ngOnInit(): void {
        this.buildFormGrup();
        this.findSeniorities();
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
            idSeniority: [null, [Validators.required]]
        });
    }

    findSeniorities(): void {
        this.seniorityService.findAll()
            .subscribe((data: DropdownModel[]) => {
                data.forEach(item => this.dropdownSeniorities.push({ value: item.id, label: item.name }))
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
            this.formGroup.patchValue(this.contributorToUpdate);
            return;
        }
        this.formGroup.get('idSeniority')?.setValue(this.dropdownSeniorities[1].value);
    }

    getTitle(): string {
        return this.editing ? 'Editar Colaborador' : 'Cadastrar Colaborador';
    }

    getLabelButtonSubmit(): string {
        return this.editing ? 'Editar' : 'Cadastrar';
    }

    submitForm(): void {
        if (this.editing) {
            this.updateContributor(this.formGroup.value);
            return;
        }
        this.createContributor(this.formGroup.value);
    }

}

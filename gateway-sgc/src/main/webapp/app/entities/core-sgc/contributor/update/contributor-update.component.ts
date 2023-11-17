import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ContributorFormService, ContributorFormGroup } from './contributor-form.service';
import { IContributor } from '../contributor.model';
import { ContributorService } from '../service/contributor.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ISeniority } from 'app/entities/core-sgc/seniority/seniority.model';
import { SeniorityService } from 'app/entities/core-sgc/seniority/service/seniority.service';
import { ICompetency } from 'app/entities/core-sgc/competency/competency.model';
import { CompetencyService } from 'app/entities/core-sgc/competency/service/competency.service';

@Component({
  standalone: true,
  selector: 'jhi-contributor-update',
  templateUrl: './contributor-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ContributorUpdateComponent implements OnInit {
  isSaving = false;
  contributor: IContributor | null = null;

  senioritiesSharedCollection: ISeniority[] = [];
  competenciesSharedCollection: ICompetency[] = [];

  editForm: ContributorFormGroup = this.contributorFormService.createContributorFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected contributorService: ContributorService,
    protected contributorFormService: ContributorFormService,
    protected seniorityService: SeniorityService,
    protected competencyService: CompetencyService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareSeniority = (o1: ISeniority | null, o2: ISeniority | null): boolean => this.seniorityService.compareSeniority(o1, o2);

  compareCompetency = (o1: ICompetency | null, o2: ICompetency | null): boolean => this.competencyService.compareCompetency(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contributor }) => {
      this.contributor = contributor;
      if (contributor) {
        this.updateForm(contributor);
      }

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('gatewaySgcApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contributor = this.contributorFormService.getContributor(this.editForm);
    if (contributor.id !== null) {
      this.subscribeToSaveResponse(this.contributorService.update(contributor));
    } else {
      this.subscribeToSaveResponse(this.contributorService.create(contributor));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContributor>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(contributor: IContributor): void {
    this.contributor = contributor;
    this.contributorFormService.resetForm(this.editForm, contributor);

    this.senioritiesSharedCollection = this.seniorityService.addSeniorityToCollectionIfMissing<ISeniority>(
      this.senioritiesSharedCollection,
      contributor.seniority
    );
    this.competenciesSharedCollection = this.competencyService.addCompetencyToCollectionIfMissing<ICompetency>(
      this.competenciesSharedCollection,
      ...(contributor.competences ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.seniorityService
      .query()
      .pipe(map((res: HttpResponse<ISeniority[]>) => res.body ?? []))
      .pipe(
        map((seniorities: ISeniority[]) =>
          this.seniorityService.addSeniorityToCollectionIfMissing<ISeniority>(seniorities, this.contributor?.seniority)
        )
      )
      .subscribe((seniorities: ISeniority[]) => (this.senioritiesSharedCollection = seniorities));

    this.competencyService
      .query()
      .pipe(map((res: HttpResponse<ICompetency[]>) => res.body ?? []))
      .pipe(
        map((competencies: ICompetency[]) =>
          this.competencyService.addCompetencyToCollectionIfMissing<ICompetency>(competencies, ...(this.contributor?.competences ?? []))
        )
      )
      .subscribe((competencies: ICompetency[]) => (this.competenciesSharedCollection = competencies));
  }
}

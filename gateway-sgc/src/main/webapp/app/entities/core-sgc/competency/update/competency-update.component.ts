import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { CompetencyFormService, CompetencyFormGroup } from './competency-form.service';
import { ICompetency } from '../competency.model';
import { CompetencyService } from '../service/competency.service';
import { ICategory } from 'app/entities/core-sgc/category/category.model';
import { CategoryService } from 'app/entities/core-sgc/category/service/category.service';

@Component({
  standalone: true,
  selector: 'jhi-competency-update',
  templateUrl: './competency-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CompetencyUpdateComponent implements OnInit {
  isSaving = false;
  competency: ICompetency | null = null;

  categoriesSharedCollection: ICategory[] = [];

  editForm: CompetencyFormGroup = this.competencyFormService.createCompetencyFormGroup();

  constructor(
    protected competencyService: CompetencyService,
    protected competencyFormService: CompetencyFormService,
    protected categoryService: CategoryService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCategory = (o1: ICategory | null, o2: ICategory | null): boolean => this.categoryService.compareCategory(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ competency }) => {
      this.competency = competency;
      if (competency) {
        this.updateForm(competency);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const competency = this.competencyFormService.getCompetency(this.editForm);
    if (competency.id !== null) {
      competency.creationDate = this.competency?.creationDate;
      this.subscribeToSaveResponse(this.competencyService.update(competency));
    } else {
      this.subscribeToSaveResponse(this.competencyService.create(competency));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompetency>>): void {
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

  protected updateForm(competency: ICompetency): void {
    this.competency = competency;
    this.competencyFormService.resetForm(this.editForm, competency);

    this.categoriesSharedCollection = this.categoryService.addCategoryToCollectionIfMissing<ICategory>(
      this.categoriesSharedCollection,
      competency.category
    );
  }

  protected loadRelationshipsOptions(): void {
    this.categoryService
      .query()
      .pipe(map((res: HttpResponse<ICategory[]>) => res.body ?? []))
      .pipe(
        map((categories: ICategory[]) =>
          this.categoryService.addCategoryToCollectionIfMissing<ICategory>(categories, this.competency?.category)
        )
      )
      .subscribe((categories: ICategory[]) => (this.categoriesSharedCollection = categories));
  }
}

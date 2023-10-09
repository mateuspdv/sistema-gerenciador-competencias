import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CompetencyFormService } from './competency-form.service';
import { CompetencyService } from '../service/competency.service';
import { ICompetency } from '../competency.model';
import { ICategory } from 'app/entities/core-sgc/category/category.model';
import { CategoryService } from 'app/entities/core-sgc/category/service/category.service';

import { CompetencyUpdateComponent } from './competency-update.component';

describe('Competency Management Update Component', () => {
  let comp: CompetencyUpdateComponent;
  let fixture: ComponentFixture<CompetencyUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let competencyFormService: CompetencyFormService;
  let competencyService: CompetencyService;
  let categoryService: CategoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), CompetencyUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(CompetencyUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CompetencyUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    competencyFormService = TestBed.inject(CompetencyFormService);
    competencyService = TestBed.inject(CompetencyService);
    categoryService = TestBed.inject(CategoryService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Category query and add missing value', () => {
      const competency: ICompetency = { id: 456 };
      const category: ICategory = { id: 27662 };
      competency.category = category;

      const categoryCollection: ICategory[] = [{ id: 5374 }];
      jest.spyOn(categoryService, 'query').mockReturnValue(of(new HttpResponse({ body: categoryCollection })));
      const additionalCategories = [category];
      const expectedCollection: ICategory[] = [...additionalCategories, ...categoryCollection];
      jest.spyOn(categoryService, 'addCategoryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ competency });
      comp.ngOnInit();

      expect(categoryService.query).toHaveBeenCalled();
      expect(categoryService.addCategoryToCollectionIfMissing).toHaveBeenCalledWith(
        categoryCollection,
        ...additionalCategories.map(expect.objectContaining)
      );
      expect(comp.categoriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const competency: ICompetency = { id: 456 };
      const category: ICategory = { id: 19388 };
      competency.category = category;

      activatedRoute.data = of({ competency });
      comp.ngOnInit();

      expect(comp.categoriesSharedCollection).toContain(category);
      expect(comp.competency).toEqual(competency);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICompetency>>();
      const competency = { id: 123 };
      jest.spyOn(competencyFormService, 'getCompetency').mockReturnValue(competency);
      jest.spyOn(competencyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ competency });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: competency }));
      saveSubject.complete();

      // THEN
      expect(competencyFormService.getCompetency).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(competencyService.update).toHaveBeenCalledWith(expect.objectContaining(competency));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICompetency>>();
      const competency = { id: 123 };
      jest.spyOn(competencyFormService, 'getCompetency').mockReturnValue({ id: null });
      jest.spyOn(competencyService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ competency: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: competency }));
      saveSubject.complete();

      // THEN
      expect(competencyFormService.getCompetency).toHaveBeenCalled();
      expect(competencyService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICompetency>>();
      const competency = { id: 123 };
      jest.spyOn(competencyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ competency });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(competencyService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCategory', () => {
      it('Should forward to categoryService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(categoryService, 'compareCategory');
        comp.compareCategory(entity, entity2);
        expect(categoryService.compareCategory).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

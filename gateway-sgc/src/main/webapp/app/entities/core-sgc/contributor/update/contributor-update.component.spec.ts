import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ContributorFormService } from './contributor-form.service';
import { ContributorService } from '../service/contributor.service';
import { IContributor } from '../contributor.model';
import { ISeniority } from 'app/entities/core-sgc/seniority/seniority.model';
import { SeniorityService } from 'app/entities/core-sgc/seniority/service/seniority.service';
import { ICompetency } from 'app/entities/core-sgc/competency/competency.model';
import { CompetencyService } from 'app/entities/core-sgc/competency/service/competency.service';

import { ContributorUpdateComponent } from './contributor-update.component';

describe('Contributor Management Update Component', () => {
  let comp: ContributorUpdateComponent;
  let fixture: ComponentFixture<ContributorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let contributorFormService: ContributorFormService;
  let contributorService: ContributorService;
  let seniorityService: SeniorityService;
  let competencyService: CompetencyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ContributorUpdateComponent],
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
      .overrideTemplate(ContributorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ContributorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    contributorFormService = TestBed.inject(ContributorFormService);
    contributorService = TestBed.inject(ContributorService);
    seniorityService = TestBed.inject(SeniorityService);
    competencyService = TestBed.inject(CompetencyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Seniority query and add missing value', () => {
      const contributor: IContributor = { id: 456 };
      const seniority: ISeniority = { id: 14419 };
      contributor.seniority = seniority;

      const seniorityCollection: ISeniority[] = [{ id: 2629 }];
      jest.spyOn(seniorityService, 'query').mockReturnValue(of(new HttpResponse({ body: seniorityCollection })));
      const additionalSeniorities = [seniority];
      const expectedCollection: ISeniority[] = [...additionalSeniorities, ...seniorityCollection];
      jest.spyOn(seniorityService, 'addSeniorityToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contributor });
      comp.ngOnInit();

      expect(seniorityService.query).toHaveBeenCalled();
      expect(seniorityService.addSeniorityToCollectionIfMissing).toHaveBeenCalledWith(
        seniorityCollection,
        ...additionalSeniorities.map(expect.objectContaining)
      );
      expect(comp.senioritiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Competency query and add missing value', () => {
      const contributor: IContributor = { id: 456 };
      const competences: ICompetency[] = [{ id: 8715 }];
      contributor.competences = competences;

      const competencyCollection: ICompetency[] = [{ id: 21137 }];
      jest.spyOn(competencyService, 'query').mockReturnValue(of(new HttpResponse({ body: competencyCollection })));
      const additionalCompetencies = [...competences];
      const expectedCollection: ICompetency[] = [...additionalCompetencies, ...competencyCollection];
      jest.spyOn(competencyService, 'addCompetencyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contributor });
      comp.ngOnInit();

      expect(competencyService.query).toHaveBeenCalled();
      expect(competencyService.addCompetencyToCollectionIfMissing).toHaveBeenCalledWith(
        competencyCollection,
        ...additionalCompetencies.map(expect.objectContaining)
      );
      expect(comp.competenciesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const contributor: IContributor = { id: 456 };
      const seniority: ISeniority = { id: 1863 };
      contributor.seniority = seniority;
      const competences: ICompetency = { id: 4186 };
      contributor.competences = [competences];

      activatedRoute.data = of({ contributor });
      comp.ngOnInit();

      expect(comp.senioritiesSharedCollection).toContain(seniority);
      expect(comp.competenciesSharedCollection).toContain(competences);
      expect(comp.contributor).toEqual(contributor);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContributor>>();
      const contributor = { id: 123 };
      jest.spyOn(contributorFormService, 'getContributor').mockReturnValue(contributor);
      jest.spyOn(contributorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contributor });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contributor }));
      saveSubject.complete();

      // THEN
      expect(contributorFormService.getContributor).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(contributorService.update).toHaveBeenCalledWith(expect.objectContaining(contributor));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContributor>>();
      const contributor = { id: 123 };
      jest.spyOn(contributorFormService, 'getContributor').mockReturnValue({ id: null });
      jest.spyOn(contributorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contributor: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contributor }));
      saveSubject.complete();

      // THEN
      expect(contributorFormService.getContributor).toHaveBeenCalled();
      expect(contributorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContributor>>();
      const contributor = { id: 123 };
      jest.spyOn(contributorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contributor });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(contributorService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareSeniority', () => {
      it('Should forward to seniorityService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(seniorityService, 'compareSeniority');
        comp.compareSeniority(entity, entity2);
        expect(seniorityService.compareSeniority).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCompetency', () => {
      it('Should forward to competencyService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(competencyService, 'compareCompetency');
        comp.compareCompetency(entity, entity2);
        expect(competencyService.compareCompetency).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

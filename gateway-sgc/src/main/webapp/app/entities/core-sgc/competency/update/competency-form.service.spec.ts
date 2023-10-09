import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../competency.test-samples';

import { CompetencyFormService } from './competency-form.service';

describe('Competency Form Service', () => {
  let service: CompetencyFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CompetencyFormService);
  });

  describe('Service methods', () => {
    describe('createCompetencyFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCompetencyFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            description: expect.any(Object),
            creationDate: expect.any(Object),
            lastUpdateDate: expect.any(Object),
            category: expect.any(Object),
          })
        );
      });

      it('passing ICompetency should create a new form with FormGroup', () => {
        const formGroup = service.createCompetencyFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            description: expect.any(Object),
            creationDate: expect.any(Object),
            lastUpdateDate: expect.any(Object),
            category: expect.any(Object),
          })
        );
      });
    });

    describe('getCompetency', () => {
      it('should return NewCompetency for default Competency initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCompetencyFormGroup(sampleWithNewData);

        const competency = service.getCompetency(formGroup) as any;

        expect(competency).toMatchObject(sampleWithNewData);
      });

      it('should return NewCompetency for empty Competency initial value', () => {
        const formGroup = service.createCompetencyFormGroup();

        const competency = service.getCompetency(formGroup) as any;

        expect(competency).toMatchObject({});
      });

      it('should return ICompetency', () => {
        const formGroup = service.createCompetencyFormGroup(sampleWithRequiredData);

        const competency = service.getCompetency(formGroup) as any;

        expect(competency).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICompetency should not enable id FormControl', () => {
        const formGroup = service.createCompetencyFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCompetency should disable id FormControl', () => {
        const formGroup = service.createCompetencyFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

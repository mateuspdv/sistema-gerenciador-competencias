import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { CompetencyDetailComponent } from './competency-detail.component';

describe('Competency Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CompetencyDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CompetencyDetailComponent,
              resolve: { competency: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding()
        ),
      ],
    })
      .overrideTemplate(CompetencyDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load competency on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CompetencyDetailComponent);

      // THEN
      expect(instance.competency).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

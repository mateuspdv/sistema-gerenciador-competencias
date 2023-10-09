import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICompetency, NewCompetency } from '../competency.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICompetency for edit and NewCompetencyFormGroupInput for create.
 */
type CompetencyFormGroupInput = ICompetency | PartialWithRequiredKeyOf<NewCompetency>;

type CompetencyFormDefaults = Pick<NewCompetency, 'id'>;

type CompetencyFormGroupContent = {
  id: FormControl<ICompetency['id'] | NewCompetency['id']>;
  name: FormControl<ICompetency['name']>;
  description: FormControl<ICompetency['description']>;
  category: FormControl<ICompetency['category']>;
};

export type CompetencyFormGroup = FormGroup<CompetencyFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CompetencyFormService {
  createCompetencyFormGroup(competency: CompetencyFormGroupInput = { id: null }): CompetencyFormGroup {
    const competencyRawValue = {
      ...this.getFormDefaults(),
      ...competency,
    };
    return new FormGroup<CompetencyFormGroupContent>({
      id: new FormControl(
        { value: competencyRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(competencyRawValue.name, {
        validators: [Validators.required, Validators.minLength(3), Validators.maxLength(50)],
      }),
      description: new FormControl(competencyRawValue.description, {
        validators: [Validators.required, Validators.minLength(3), Validators.maxLength(50)],
      }),
      category: new FormControl(competencyRawValue.category, {
        validators: [Validators.required],
      }),
    });
  }

  getCompetency(form: CompetencyFormGroup): ICompetency | NewCompetency {
    return form.getRawValue() as ICompetency | NewCompetency;
  }

  resetForm(form: CompetencyFormGroup, competency: CompetencyFormGroupInput): void {
    const competencyRawValue = { ...this.getFormDefaults(), ...competency };
    form.reset(
      {
        ...competencyRawValue,
        id: { value: competencyRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CompetencyFormDefaults {
    return {
      id: null,
    };
  }
}

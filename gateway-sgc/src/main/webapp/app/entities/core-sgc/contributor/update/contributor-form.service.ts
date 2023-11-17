import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IContributor, NewContributor } from '../contributor.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IContributor for edit and NewContributorFormGroupInput for create.
 */
type ContributorFormGroupInput = IContributor | PartialWithRequiredKeyOf<NewContributor>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IContributor | NewContributor> = Omit<T, 'creationDate' | 'lastUpdateDate'> & {
  creationDate?: string | null;
  lastUpdateDate?: string | null;
};

type ContributorFormRawValue = FormValueOf<IContributor>;

type NewContributorFormRawValue = FormValueOf<NewContributor>;

type ContributorFormDefaults = Pick<NewContributor, 'id' | 'creationDate' | 'lastUpdateDate' | 'competences'>;

type ContributorFormGroupContent = {
  id: FormControl<ContributorFormRawValue['id'] | NewContributor['id']>;
  name: FormControl<ContributorFormRawValue['name']>;
  lastName: FormControl<ContributorFormRawValue['lastName']>;
  cpf: FormControl<ContributorFormRawValue['cpf']>;
  email: FormControl<ContributorFormRawValue['email']>;
  photo: FormControl<ContributorFormRawValue['photo']>;
  photoContentType: FormControl<ContributorFormRawValue['photoContentType']>;
  birthDate: FormControl<ContributorFormRawValue['birthDate']>;
  admissionDate: FormControl<ContributorFormRawValue['admissionDate']>;
  creationDate: FormControl<ContributorFormRawValue['creationDate']>;
  lastUpdateDate: FormControl<ContributorFormRawValue['lastUpdateDate']>;
  seniority: FormControl<ContributorFormRawValue['seniority']>;
  competences: FormControl<ContributorFormRawValue['competences']>;
};

export type ContributorFormGroup = FormGroup<ContributorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ContributorFormService {
  createContributorFormGroup(contributor: ContributorFormGroupInput = { id: null }): ContributorFormGroup {
    const contributorRawValue = this.convertContributorToContributorRawValue({
      ...this.getFormDefaults(),
      ...contributor,
    });
    return new FormGroup<ContributorFormGroupContent>({
      id: new FormControl(
        { value: contributorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(contributorRawValue.name, {
        validators: [Validators.required, Validators.minLength(3), Validators.maxLength(50)],
      }),
      lastName: new FormControl(contributorRawValue.lastName, {
        validators: [Validators.required, Validators.minLength(3), Validators.maxLength(50)],
      }),
      cpf: new FormControl(contributorRawValue.cpf, {
        validators: [Validators.required, Validators.minLength(11), Validators.maxLength(11)],
      }),
      email: new FormControl(contributorRawValue.email, {
        validators: [Validators.required, Validators.minLength(3), Validators.maxLength(50)],
      }),
      photo: new FormControl(contributorRawValue.photo, {
        validators: [Validators.required],
      }),
      photoContentType: new FormControl(contributorRawValue.photoContentType),
      birthDate: new FormControl(contributorRawValue.birthDate, {
        validators: [Validators.required],
      }),
      admissionDate: new FormControl(contributorRawValue.admissionDate, {
        validators: [Validators.required],
      }),
      creationDate: new FormControl(contributorRawValue.creationDate, {
        validators: [Validators.required],
      }),
      lastUpdateDate: new FormControl(contributorRawValue.lastUpdateDate, {
        validators: [Validators.required],
      }),
      seniority: new FormControl(contributorRawValue.seniority, {
        validators: [Validators.required],
      }),
      competences: new FormControl(contributorRawValue.competences ?? []),
    });
  }

  getContributor(form: ContributorFormGroup): IContributor | NewContributor {
    return this.convertContributorRawValueToContributor(form.getRawValue() as ContributorFormRawValue | NewContributorFormRawValue);
  }

  resetForm(form: ContributorFormGroup, contributor: ContributorFormGroupInput): void {
    const contributorRawValue = this.convertContributorToContributorRawValue({ ...this.getFormDefaults(), ...contributor });
    form.reset(
      {
        ...contributorRawValue,
        id: { value: contributorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ContributorFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      creationDate: currentTime,
      lastUpdateDate: currentTime,
      competences: [],
    };
  }

  private convertContributorRawValueToContributor(
    rawContributor: ContributorFormRawValue | NewContributorFormRawValue
  ): IContributor | NewContributor {
    return {
      ...rawContributor,
      creationDate: dayjs(rawContributor.creationDate, DATE_TIME_FORMAT),
      lastUpdateDate: dayjs(rawContributor.lastUpdateDate, DATE_TIME_FORMAT),
    };
  }

  private convertContributorToContributorRawValue(
    contributor: IContributor | (Partial<NewContributor> & ContributorFormDefaults)
  ): ContributorFormRawValue | PartialWithRequiredKeyOf<NewContributorFormRawValue> {
    return {
      ...contributor,
      creationDate: contributor.creationDate ? contributor.creationDate.format(DATE_TIME_FORMAT) : undefined,
      lastUpdateDate: contributor.lastUpdateDate ? contributor.lastUpdateDate.format(DATE_TIME_FORMAT) : undefined,
      competences: contributor.competences ?? [],
    };
  }
}

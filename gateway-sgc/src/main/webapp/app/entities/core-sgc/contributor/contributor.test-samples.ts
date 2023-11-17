import dayjs from 'dayjs/esm';

import { IContributor, NewContributor } from './contributor.model';

export const sampleWithRequiredData: IContributor = {
  id: 11066,
  name: 'Filmes',
  lastName: 'Martins',
  cpf: 'LuxemburgoX',
  email: 'Maria.Albuquerque@live.com',
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
  birthDate: dayjs('2023-10-23'),
  admissionDate: dayjs('2023-10-24'),
  creationDate: dayjs('2023-10-24T06:36'),
  lastUpdateDate: dayjs('2023-10-23T20:47'),
};

export const sampleWithPartialData: IContributor = {
  id: 4454,
  name: 'Jóias Fantástico',
  lastName: 'Pereira',
  cpf: 'Batista Mér',
  email: 'Vitor67@gmail.com',
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
  birthDate: dayjs('2023-10-24'),
  admissionDate: dayjs('2023-10-23'),
  creationDate: dayjs('2023-10-24T11:02'),
  lastUpdateDate: dayjs('2023-10-24T08:11'),
};

export const sampleWithFullData: IContributor = {
  id: 24079,
  name: 'Pizza Usabilidade Produtor',
  lastName: 'Reis',
  cpf: 'Alameda Inc',
  email: 'Davi_Pereira@live.com',
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
  birthDate: dayjs('2023-10-23'),
  admissionDate: dayjs('2023-10-24'),
  creationDate: dayjs('2023-10-24T08:48'),
  lastUpdateDate: dayjs('2023-10-23T22:48'),
};

export const sampleWithNewData: NewContributor = {
  name: 'Ergonômico',
  lastName: 'Pereira',
  cpf: 'MadeiraXXXX',
  email: 'Feliciano42@yahoo.com',
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
  birthDate: dayjs('2023-10-23'),
  admissionDate: dayjs('2023-10-24'),
  creationDate: dayjs('2023-10-24T02:34'),
  lastUpdateDate: dayjs('2023-10-24T07:23'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

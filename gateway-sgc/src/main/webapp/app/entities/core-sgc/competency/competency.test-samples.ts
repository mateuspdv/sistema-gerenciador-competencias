import dayjs from 'dayjs/esm';

import { ICompetency, NewCompetency } from './competency.model';

export const sampleWithRequiredData: ICompetency = {
  id: 15459,
  name: 'Engenheiro Implementation',
  description: 'Sudão',
  creationDate: dayjs('2023-10-08'),
  lastUpdateDate: dayjs('2023-10-07'),
};

export const sampleWithPartialData: ICompetency = {
  id: 30367,
  name: 'ad Ilhas',
  description: 'Planejador Metal Futuro',
  creationDate: dayjs('2023-10-08'),
  lastUpdateDate: dayjs('2023-10-08'),
};

export const sampleWithFullData: ICompetency = {
  id: 15824,
  name: 'Incrível',
  description: 'Mesa Rodovia Diretivas',
  creationDate: dayjs('2023-10-08'),
  lastUpdateDate: dayjs('2023-10-07'),
};

export const sampleWithNewData: NewCompetency = {
  name: 'possimus verde Salsicha',
  description: 'Breno',
  creationDate: dayjs('2023-10-08'),
  lastUpdateDate: dayjs('2023-10-08'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

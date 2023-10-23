import { ISkill, NewSkill } from './skill.model';

export const sampleWithRequiredData: ISkill = {
  id: 29403,
  description: 'Programa',
};

export const sampleWithPartialData: ISkill = {
  id: 10297,
  description: 'Agente',
};

export const sampleWithFullData: ISkill = {
  id: 20548,
  description: 'maxime Corporativo',
};

export const sampleWithNewData: NewSkill = {
  description: 'Esportes Jóias Santos',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

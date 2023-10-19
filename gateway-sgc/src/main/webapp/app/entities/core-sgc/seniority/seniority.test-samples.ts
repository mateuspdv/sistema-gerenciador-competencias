import { ISeniority, NewSeniority } from './seniority.model';

export const sampleWithRequiredData: ISeniority = {
  id: 189,
  description: 'International madeira Bicicleta',
};

export const sampleWithPartialData: ISeniority = {
  id: 12097,
  description: 'Egito nam Marca',
};

export const sampleWithFullData: ISeniority = {
  id: 5492,
  description: 'Feminino',
};

export const sampleWithNewData: NewSeniority = {
  description: 'Livros',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

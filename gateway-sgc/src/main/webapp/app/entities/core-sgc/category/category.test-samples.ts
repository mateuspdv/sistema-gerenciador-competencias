import { ICategory, NewCategory } from './category.model';

export const sampleWithRequiredData: ICategory = {
  id: 7142,
  name: 'Congelado',
};

export const sampleWithPartialData: ICategory = {
  id: 6084,
  name: 'Licenciado Irã Programa',
};

export const sampleWithFullData: ICategory = {
  id: 4235,
  name: 'violeta quis Feminino',
};

export const sampleWithNewData: NewCategory = {
  name: 'Pizza',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

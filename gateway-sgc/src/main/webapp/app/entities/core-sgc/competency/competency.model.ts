import dayjs from 'dayjs/esm';
import { ICategory } from 'app/entities/core-sgc/category/category.model';

export interface ICompetency {
  id: number;
  name?: string | null;
  description?: string | null;
  creationDate?: dayjs.Dayjs | null;
  lastUpdateDate?: dayjs.Dayjs | null;
  category?: ICategory | null;
}

export type NewCompetency = Omit<ICompetency, 'id'> & { id: null };

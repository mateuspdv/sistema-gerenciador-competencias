export interface ICategory {
  id: number;
  name: string;
}

export type NewCategory = Omit<ICategory, 'id'> & { id: null };

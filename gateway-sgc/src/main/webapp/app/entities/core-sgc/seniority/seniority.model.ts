export interface ISeniority {
  id: number;
  description?: string | null;
}

export type NewSeniority = Omit<ISeniority, 'id'> & { id: null };

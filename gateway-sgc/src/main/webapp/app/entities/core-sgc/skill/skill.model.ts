export interface ISkill {
  id: number;
  description?: string | null;
}

export type NewSkill = Omit<ISkill, 'id'> & { id: null };

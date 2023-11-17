import dayjs from 'dayjs/esm';
import { ISeniority } from 'app/entities/core-sgc/seniority/seniority.model';
import { ICompetency } from 'app/entities/core-sgc/competency/competency.model';

export interface IContributor {
  id: number;
  name?: string | null;
  lastName?: string | null;
  cpf?: string | null;
  email?: string | null;
  photo?: string | null;
  photoContentType?: string | null;
  birthDate?: dayjs.Dayjs | null;
  admissionDate?: dayjs.Dayjs | null;
  creationDate?: dayjs.Dayjs | null;
  lastUpdateDate?: dayjs.Dayjs | null;
  seniority?: Pick<ISeniority, 'id' | 'description'> | null;
  competences?: Pick<ICompetency, 'id' | 'name'>[] | null;
}

export type NewContributor = Omit<IContributor, 'id'> & { id: null };

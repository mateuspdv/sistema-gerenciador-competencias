import { ContributorCompetencyModel } from './contributor-competency.model';
export class ContributorModel {
    id?: number;
    firstName?: string;
    lastName?: string;
    cpf?: string;
    email?: string;
    // photo
    birthDate: Date = new Date();
    admissionDate: Date = new Date();
    idSeniority?: number;
    nameSeniority?: string;
    competencies: ContributorCompetencyModel[] = []
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISkill, NewSkill } from '../skill.model';

export type PartialUpdateSkill = Partial<ISkill> & Pick<ISkill, 'id'>;

export type EntityResponseType = HttpResponse<ISkill>;
export type EntityArrayResponseType = HttpResponse<ISkill[]>;

@Injectable({ providedIn: 'root' })
export class SkillService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/skills', 'core-sgc');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISkill>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISkill[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  getSkillIdentifier(skill: Pick<ISkill, 'id'>): number {
    return skill.id;
  }

  compareSkill(o1: Pick<ISkill, 'id'> | null, o2: Pick<ISkill, 'id'> | null): boolean {
    return o1 && o2 ? this.getSkillIdentifier(o1) === this.getSkillIdentifier(o2) : o1 === o2;
  }

  addSkillToCollectionIfMissing<Type extends Pick<ISkill, 'id'>>(
    skillCollection: Type[],
    ...skillsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const skills: Type[] = skillsToCheck.filter(isPresent);
    if (skills.length > 0) {
      const skillCollectionIdentifiers = skillCollection.map(skillItem => this.getSkillIdentifier(skillItem)!);
      const skillsToAdd = skills.filter(skillItem => {
        const skillIdentifier = this.getSkillIdentifier(skillItem);
        if (skillCollectionIdentifiers.includes(skillIdentifier)) {
          return false;
        }
        skillCollectionIdentifiers.push(skillIdentifier);
        return true;
      });
      return [...skillsToAdd, ...skillCollection];
    }
    return skillCollection;
  }
}

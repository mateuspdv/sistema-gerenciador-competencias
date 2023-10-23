package br.com.coresgc.service.mapper;

import br.com.coresgc.domain.Skill;
import br.com.coresgc.service.dto.SkillDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Skill} and its DTO {@link SkillDTO}.
 */
@Mapper(componentModel = "spring")
public interface SkillMapper extends EntityMapper<SkillDTO, Skill> {
}

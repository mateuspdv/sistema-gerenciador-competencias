package br.com.coresgc.service.mapper;

import br.com.coresgc.domain.Seniority;
import br.com.coresgc.service.dto.SeniorityDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Seniority} and its DTO {@link SeniorityDTO}.
 */
@Mapper(componentModel = "spring")
public interface SeniorityMapper extends EntityMapper<SeniorityDTO, Seniority> {
}

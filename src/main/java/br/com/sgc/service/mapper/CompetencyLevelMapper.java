package br.com.sgc.service.mapper;

import br.com.sgc.domain.CompetencyLevel;
import br.com.sgc.service.dto.CompetencyLevelDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompetencyLevelMapper extends EntityMapper<CompetencyLevelDto, CompetencyLevel> {
}

package br.com.sgc.service.mapper;

import br.com.sgc.model.LevelCompetencyModel;
import br.com.sgc.service.dto.LevelCompetencyDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LevelCompetencyMapper extends EntityMapper<LevelCompetencyDto, LevelCompetencyModel> {
}

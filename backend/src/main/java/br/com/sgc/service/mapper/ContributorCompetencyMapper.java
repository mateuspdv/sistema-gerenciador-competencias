package br.com.sgc.service.mapper;

import br.com.sgc.model.ContributorCompetencyModel;
import br.com.sgc.service.dto.ContributorCompetencyDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContributorCompetencyMapper extends EntityMapper<ContributorCompetencyDto, ContributorCompetencyModel> {

    @Override
    @Mapping(source = "competencyModel.id", target = "idCompetency")
    @Mapping(source = "competencyModel.name", target = "nameCompetency")
    @Mapping(source = "levelCompetencyModel.id", target = "idLevelCompetency")
    @Mapping(source = "levelCompetencyModel.name", target = "nameLevelCompetency")
    ContributorCompetencyDto toDto(ContributorCompetencyModel entity);

    @Override
    @Mapping(source = "idCompetency", target = "competencyModel.id")
    @Mapping(source = "nameCompetency", target = "competencyModel.name")
    @Mapping(source = "idLevelCompetency", target = "levelCompetencyModel.id")
    @Mapping(source = "nameLevelCompetency", target = "levelCompetencyModel.name")
    ContributorCompetencyModel toEntity(ContributorCompetencyDto dto);

}

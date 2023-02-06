package br.com.sgc.service.mapper;

import br.com.sgc.model.ContributorModel;
import br.com.sgc.service.dto.ContributorDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ContributorCompetencyMapper.class})
public interface ContributorMapper extends EntityMapper<ContributorDto, ContributorModel> {

    @Override
    @Mapping(source = "seniorityModel.id", target = "idSeniority")
    @Mapping(source = "seniorityModel.name", target = "nameSeniority")
    @Mapping(source = "contributorCompetencyModel", target = "competencies")
    ContributorDto toDto(ContributorModel entity);

    @Override
    @Mapping(source = "idSeniority", target = "seniorityModel.id")
    @Mapping(source = "nameSeniority", target = "seniorityModel.name")
    @Mapping(source = "competencies", target = "contributorCompetencyModel")
    ContributorModel toEntity(ContributorDto dto);

}

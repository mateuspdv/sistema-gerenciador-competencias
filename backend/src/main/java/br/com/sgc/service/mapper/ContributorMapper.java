package br.com.sgc.service.mapper;

import br.com.sgc.model.ContributorModel;
import br.com.sgc.service.dto.ContributorDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContributorMapper extends EntityMapper<ContributorDto, ContributorModel> {

    @Override
    @Mapping(source = "seniorityModel.id", target = "idSeniority")
    @Mapping(source = "seniorityModel.name", target = "nameSeniority")
    ContributorDto toDto(ContributorModel entity);

    @Override
    @Mapping(source = "idSeniority", target = "seniorityModel.id")
    @Mapping(source = "nameSeniority", target = "seniorityModel.name")
    ContributorModel toEntity(ContributorDto dto);

}

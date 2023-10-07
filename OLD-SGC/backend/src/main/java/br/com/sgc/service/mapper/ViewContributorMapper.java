package br.com.sgc.service.mapper;

import br.com.sgc.model.ContributorModel;
import br.com.sgc.service.dto.ViewContributorDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ViewContributorMapper extends EntityMapper<ViewContributorDto, ContributorModel> {

    @Override
    @Mapping(source = "seniorityModel.name", target = "nameSeniority")
    ViewContributorDto toDto(ContributorModel entity);

    @Override
    @Mapping(source = "nameSeniority", target = "seniorityModel.name")
    ContributorModel toEntity(ViewContributorDto dto);

}

package br.com.sgc.service.mapper;

import br.com.sgc.domain.Competency;
import br.com.sgc.service.dto.CompetencyDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompetencyMapper extends EntityMapper<CompetencyDto, Competency> {

    @Override
    @Mapping(source = "idCategory", target = "category.id")
    Competency toEntity(CompetencyDto dto);

    @Override
    @Mapping(source = "category.id", target = "idCategory")
    CompetencyDto toDto(Competency entity);

}

package br.com.sgc.service.mapper;

import br.com.sgc.model.CompetencyModel;
import br.com.sgc.service.dto.CompetencyDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompetencyMapper extends EntityMapper<CompetencyDto, CompetencyModel>{

    @Override
    @Mapping(source = "categoryModel.id", target = "idCategory")
    CompetencyDto toDto(CompetencyModel entity);

    @Override
    @Mapping(source = "idCategory", target = "categoryModel.id")
    CompetencyModel toEntity(CompetencyDto dto);

}

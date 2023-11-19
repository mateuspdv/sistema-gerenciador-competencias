package br.com.coresgc.service.mapper;

import br.com.coresgc.domain.Competency;
import br.com.coresgc.service.dto.CompetencyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Competency} and its DTO {@link CompetencyDTO}.
 */
@Mapper(componentModel = "spring")
public interface CompetencyMapper extends EntityMapper<CompetencyDTO, Competency> {

    @Mapping(source = "category.id", target = "idCategory")
    CompetencyDTO toDto(Competency entity);

    @Mapping(source = "idCategory", target = "category.id")
    Competency toEntity(CompetencyDTO dto);

}

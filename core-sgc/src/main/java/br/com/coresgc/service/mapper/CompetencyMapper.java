package br.com.coresgc.service.mapper;

import br.com.coresgc.domain.Category;
import br.com.coresgc.domain.Competency;
import br.com.coresgc.service.dto.CategoryDTO;
import br.com.coresgc.service.dto.CompetencyDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link Competency} and its DTO {@link CompetencyDTO}.
 */
@Mapper(componentModel = "spring")
public interface CompetencyMapper extends EntityMapper<CompetencyDTO, Competency> {

    CompetencyDTO toDto(Competency s);

    @Named("categoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CategoryDTO toDtoCategoryId(Category category);

}

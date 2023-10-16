package br.com.coresgc.service.mapper;

import br.com.coresgc.domain.Category;
import br.com.coresgc.service.dto.CategoryDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Category} and its DTO {@link CategoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {
}

package br.com.sgc.service.mapper;

import br.com.sgc.domain.Category;
import br.com.sgc.service.dto.CategoryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends EntityMapper<CategoryDto, Category> {
}

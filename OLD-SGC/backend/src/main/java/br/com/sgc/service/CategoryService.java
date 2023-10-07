package br.com.sgc.service;

import br.com.sgc.service.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> findAll();

    CategoryDto findById(Long idCategory);

    void existsById(Long idCategory);

}

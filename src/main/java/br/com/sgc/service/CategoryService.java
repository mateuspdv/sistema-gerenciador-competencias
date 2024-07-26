package br.com.sgc.service;

import br.com.sgc.service.dto.CategoryDto;
import br.com.sgc.service.dto.DropdownDto;

import java.util.List;

public interface CategoryService {

    List<DropdownDto> findAll();

    CategoryDto findById(Long id);

}

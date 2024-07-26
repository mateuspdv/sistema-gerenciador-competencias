package br.com.sgc.service.impl;

import br.com.sgc.repository.CategoryRepository;
import br.com.sgc.service.CategoryService;
import br.com.sgc.service.dto.CategoryDto;
import br.com.sgc.service.dto.DropdownDto;
import br.com.sgc.service.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    public List<DropdownDto> findAll() {
        return categoryRepository.findAll().stream().map(category ->
                new DropdownDto(category.getDescription(), category.getId())).toList();
    }

    public CategoryDto findById(Long id) {
        return categoryMapper.toDto(categoryRepository.findById(id)
                .orElseThrow(RuntimeException::new));
    }

}

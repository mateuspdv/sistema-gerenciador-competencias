package br.com.sgc.service.impl;

import br.com.sgc.repository.CategoryRepository;
import br.com.sgc.service.CategoryService;
import br.com.sgc.service.dto.CategoryDto;
import br.com.sgc.service.exception.EntityNotFoundException;
import br.com.sgc.service.mapper.CategoryMapper;
import br.com.sgc.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public List<CategoryDto> findAll() {
        return categoryMapper.toDto(categoryRepository.findAll());
    }

    @Transactional(readOnly = true)
    public CategoryDto findById(Long idCategory) {
        return categoryMapper.toDto(categoryRepository.findById(idCategory)
                .orElseThrow(() -> new EntityNotFoundException(MessageUtil.CATEGORY_NOT_FOUND)));
    }

    @Transactional(readOnly = true)
    public void existsById(Long idCategory) {
        if (!categoryRepository.existsById(idCategory)) {
            throw new EntityNotFoundException(MessageUtil.CATEGORY_NOT_FOUND);
        }
    }

}

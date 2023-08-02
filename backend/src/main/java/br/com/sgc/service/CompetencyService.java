package br.com.sgc.service;

import br.com.sgc.service.dto.CompetencyDto;
import br.com.sgc.service.dto.DropdownCategoryDto;
import br.com.sgc.service.dto.filter.CompetencyFilterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompetencyService {

    Page<CompetencyDto> findAll(Pageable pageable);

    Page<CompetencyDto> globalSearchFilter(Pageable pageable, String query);

    List<DropdownCategoryDto> findAllDropdown();

    CompetencyDto findById(Long idCompetency);

    CompetencyDto create(CompetencyDto competencyDto);

    CompetencyDto update(CompetencyDto competencyDto);

    void deleteById(Long idCompetency);

    Page<CompetencyDto> columnsFilter(Pageable pageable, CompetencyFilterDto filter);

}

package br.com.sgc.service;

import br.com.sgc.service.dto.CompetencyLevelDto;
import br.com.sgc.service.dto.DropdownDto;

import java.util.List;

public interface CompetencyLevelService {

    List<DropdownDto> findAll();

    CompetencyLevelDto findById(Long id);

}

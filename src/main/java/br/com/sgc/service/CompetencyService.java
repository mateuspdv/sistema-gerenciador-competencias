package br.com.sgc.service;

import br.com.sgc.domain.Competency;
import br.com.sgc.service.dto.CompetencyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompetencyService {

    Page<CompetencyDto> findAll(Pageable pageable);

    CompetencyDto findById(Long id);

    Competency findEntityById(Long id);

    CompetencyDto create(CompetencyDto competencyDto);

    CompetencyDto update(CompetencyDto competencyDto);

    void disableById(Long id);

}

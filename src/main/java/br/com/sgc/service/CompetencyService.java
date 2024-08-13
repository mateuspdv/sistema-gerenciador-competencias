package br.com.sgc.service;

import br.com.sgc.domain.Competency;
import br.com.sgc.service.dto.CompetencyDto;
import br.com.sgc.service.filter.CompetencyFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompetencyService {

    Page<CompetencyDto> filter(CompetencyFilter filter, Pageable pageable);

    CompetencyDto findById(Long id);

    Competency findEntityById(Long id);

    CompetencyDto create(CompetencyDto competencyDto);

    CompetencyDto update(CompetencyDto competencyDto);

    void disableById(Long id);

}

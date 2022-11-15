package br.com.sgc.service;

import br.com.sgc.service.dto.CompetencyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompetencyService {

    Page<CompetencyDto> findAll(Pageable pageable);

    CompetencyDto findById(Long idCompetency);

    CompetencyDto create(CompetencyDto competencyDto);

    CompetencyDto update(CompetencyDto competencyDto);

    void deleteById(Long idCompetency);

}

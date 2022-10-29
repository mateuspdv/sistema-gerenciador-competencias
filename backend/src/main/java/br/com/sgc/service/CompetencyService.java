package br.com.sgc.service;

import br.com.sgc.service.dto.CompetencyDto;

import java.util.List;

public interface CompetencyService {

    List<CompetencyDto> findAll();

    CompetencyDto findById(Long idCompetency);

    CompetencyDto create(CompetencyDto competencyDto);

    CompetencyDto update(CompetencyDto competencyDto);

    void deleteById(Long idCompetency);

}

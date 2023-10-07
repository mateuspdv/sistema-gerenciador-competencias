package br.com.sgc.service;

import br.com.sgc.service.dto.LevelCompetencyDto;

import java.util.List;

public interface LevelCompetencyService {

    List<LevelCompetencyDto> findAll();

    LevelCompetencyDto findById(Long idLevelCompetency);

}

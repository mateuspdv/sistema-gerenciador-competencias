package br.com.coresgc.service;

import br.com.coresgc.service.dto.CompetencyDTO;
import br.com.coresgc.service.dto.ViewCompetencyDTO;

import java.util.List;
import java.util.Optional;

public interface CompetencyService {

    CompetencyDTO save(CompetencyDTO competencyDTO);

    CompetencyDTO update(CompetencyDTO competencyDTO);

    Optional<CompetencyDTO> partialUpdate(CompetencyDTO competencyDTO);

    List<ViewCompetencyDTO> findAll();

    Optional<CompetencyDTO> findOne(Long id);

    void delete(Long id);

    CompetencyDTO saveRefactored(CompetencyDTO competencyDTO);

    CompetencyDTO updateRefactored(CompetencyDTO competencyDTO);

    CompetencyDTO findByIdRefactored(Long id);

}

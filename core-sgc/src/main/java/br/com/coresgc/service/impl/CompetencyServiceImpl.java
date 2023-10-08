package br.com.coresgc.service.impl;

import br.com.coresgc.domain.Competency;
import br.com.coresgc.repository.CompetencyRepository;
import br.com.coresgc.service.CompetencyService;
import br.com.coresgc.service.dto.CompetencyDTO;
import br.com.coresgc.service.mapper.CompetencyMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Competency}.
 */
@Service
@Transactional
public class CompetencyServiceImpl implements CompetencyService {

    private final Logger log = LoggerFactory.getLogger(CompetencyServiceImpl.class);

    private final CompetencyRepository competencyRepository;

    private final CompetencyMapper competencyMapper;

    public CompetencyServiceImpl(CompetencyRepository competencyRepository, CompetencyMapper competencyMapper) {
        this.competencyRepository = competencyRepository;
        this.competencyMapper = competencyMapper;
    }

    @Override
    public CompetencyDTO save(CompetencyDTO competencyDTO) {
        log.debug("Request to save Competency : {}", competencyDTO);
        Competency competency = competencyMapper.toEntity(competencyDTO);
        competency = competencyRepository.save(competency);
        return competencyMapper.toDto(competency);
    }

    @Override
    public CompetencyDTO update(CompetencyDTO competencyDTO) {
        log.debug("Request to update Competency : {}", competencyDTO);
        Competency competency = competencyMapper.toEntity(competencyDTO);
        competency = competencyRepository.save(competency);
        return competencyMapper.toDto(competency);
    }

    @Override
    public Optional<CompetencyDTO> partialUpdate(CompetencyDTO competencyDTO) {
        log.debug("Request to partially update Competency : {}", competencyDTO);

        return competencyRepository
            .findById(competencyDTO.getId())
            .map(existingCompetency -> {
                competencyMapper.partialUpdate(existingCompetency, competencyDTO);

                return existingCompetency;
            })
            .map(competencyRepository::save)
            .map(competencyMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompetencyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Competencies");
        return competencyRepository.findAll(pageable).map(competencyMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CompetencyDTO> findOne(Long id) {
        log.debug("Request to get Competency : {}", id);
        return competencyRepository.findById(id).map(competencyMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Competency : {}", id);
        competencyRepository.deleteById(id);
    }
}

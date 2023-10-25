package br.com.coresgc.service.impl;

import br.com.coresgc.domain.Contributor;
import br.com.coresgc.repository.ContributorRepository;
import br.com.coresgc.service.ContributorService;
import br.com.coresgc.service.dto.ContributorDTO;
import br.com.coresgc.service.mapper.ContributorMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Contributor}.
 */
@Service
@Transactional
public class ContributorServiceImpl implements ContributorService {

    private final Logger log = LoggerFactory.getLogger(ContributorServiceImpl.class);

    private final ContributorRepository contributorRepository;

    private final ContributorMapper contributorMapper;

    public ContributorServiceImpl(ContributorRepository contributorRepository, ContributorMapper contributorMapper) {
        this.contributorRepository = contributorRepository;
        this.contributorMapper = contributorMapper;
    }

    @Override
    public ContributorDTO save(ContributorDTO contributorDTO) {
        log.debug("Request to save Contributor : {}", contributorDTO);
        Contributor contributor = contributorMapper.toEntity(contributorDTO);
        contributor = contributorRepository.save(contributor);
        return contributorMapper.toDto(contributor);
    }

    @Override
    public ContributorDTO update(ContributorDTO contributorDTO) {
        log.debug("Request to update Contributor : {}", contributorDTO);
        Contributor contributor = contributorMapper.toEntity(contributorDTO);
        contributor = contributorRepository.save(contributor);
        return contributorMapper.toDto(contributor);
    }

    @Override
    public Optional<ContributorDTO> partialUpdate(ContributorDTO contributorDTO) {
        log.debug("Request to partially update Contributor : {}", contributorDTO);

        return contributorRepository
            .findById(contributorDTO.getId())
            .map(existingContributor -> {
                contributorMapper.partialUpdate(existingContributor, contributorDTO);

                return existingContributor;
            })
            .map(contributorRepository::save)
            .map(contributorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContributorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Contributors");
        return contributorRepository.findAll(pageable).map(contributorMapper::toDto);
    }

    public Page<ContributorDTO> findAllWithEagerRelationships(Pageable pageable) {
        return contributorRepository.findAllWithEagerRelationships(pageable).map(contributorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ContributorDTO> findOne(Long id) {
        log.debug("Request to get Contributor : {}", id);
        return contributorRepository.findOneWithEagerRelationships(id).map(contributorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Contributor : {}", id);
        contributorRepository.deleteById(id);
    }

}

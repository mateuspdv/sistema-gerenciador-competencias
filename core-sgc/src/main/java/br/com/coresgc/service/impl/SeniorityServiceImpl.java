package br.com.coresgc.service.impl;

import br.com.coresgc.domain.Seniority;
import br.com.coresgc.repository.SeniorityRepository;
import br.com.coresgc.service.SeniorityService;
import br.com.coresgc.service.dto.SeniorityDTO;
import br.com.coresgc.service.mapper.SeniorityMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Seniority}.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SeniorityServiceImpl implements SeniorityService {

    private final Logger log = LoggerFactory.getLogger(SeniorityServiceImpl.class);

    private final SeniorityRepository seniorityRepository;

    private final SeniorityMapper seniorityMapper;

    @Override
    public SeniorityDTO save(SeniorityDTO seniorityDTO) {
        log.debug("Request to save Seniority : {}", seniorityDTO);
        Seniority seniority = seniorityMapper.toEntity(seniorityDTO);
        seniority = seniorityRepository.save(seniority);
        return seniorityMapper.toDto(seniority);
    }

    @Override
    public SeniorityDTO update(SeniorityDTO seniorityDTO) {
        log.debug("Request to update Seniority : {}", seniorityDTO);
        Seniority seniority = seniorityMapper.toEntity(seniorityDTO);
        seniority = seniorityRepository.save(seniority);
        return seniorityMapper.toDto(seniority);
    }

    @Override
    public Optional<SeniorityDTO> partialUpdate(SeniorityDTO seniorityDTO) {
        log.debug("Request to partially update Seniority : {}", seniorityDTO);

        return seniorityRepository
            .findById(seniorityDTO.getId())
            .map(existingSeniority -> {
                seniorityMapper.partialUpdate(existingSeniority, seniorityDTO);

                return existingSeniority;
            })
            .map(seniorityRepository::save)
            .map(seniorityMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SeniorityDTO> findAll() {
        log.debug("Request to get all Seniorities");
        return seniorityRepository.findAll().stream().map(seniorityMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SeniorityDTO> findOne(Long id) {
        log.debug("Request to get Seniority : {}", id);
        return seniorityRepository.findById(id).map(seniorityMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Seniority : {}", id);
        seniorityRepository.deleteById(id);
    }

}

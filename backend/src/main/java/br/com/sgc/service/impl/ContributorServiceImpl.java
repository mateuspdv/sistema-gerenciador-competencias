package br.com.sgc.service.impl;

import br.com.sgc.repository.ContributorRepository;
import br.com.sgc.service.ContributorService;
import br.com.sgc.service.SeniorityService;
import br.com.sgc.service.dto.ContributorDto;
import br.com.sgc.service.dto.ViewContributorDto;
import br.com.sgc.service.exception.EntityNotFoundException;
import br.com.sgc.service.mapper.ContributorMapper;
import br.com.sgc.service.mapper.ViewContributorMapper;
import br.com.sgc.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ContributorServiceImpl implements ContributorService {

    private final SeniorityService seniorityService;

    private final ContributorRepository contributorRepository;

    private final ContributorMapper contributorMapper;

    private final ViewContributorMapper viewContributorMapper;

    public Page<ViewContributorDto> findAll(Pageable pageable) {
        return contributorRepository.findAll(pageable).map(viewContributorMapper::toDto);
    }

    public ContributorDto findById(Long idContributor) {
        return contributorMapper.toDto(contributorRepository.findById(idContributor)
                .orElseThrow(() -> new EntityNotFoundException(MessageUtil.CONTRIBUTOR_NOT_FOUND)));
    }

    public void existsById(Long idContributor) {
        if (!contributorRepository.existsById(idContributor)) {
            throw new EntityNotFoundException(MessageUtil.CONTRIBUTOR_NOT_FOUND);
        }
    }

    public ContributorDto create(ContributorDto contributorDto) {
        seniorityService.existsById(contributorDto.getIdSeniority());
        return contributorMapper.toDto(contributorRepository.save(contributorMapper.toEntity(contributorDto)));
    }

    public ContributorDto update(ContributorDto contributorDto) {
        existsById(contributorDto.getId());
        seniorityService.existsById(contributorDto.getIdSeniority());
        return contributorMapper.toDto(contributorRepository.save(contributorMapper.toEntity(contributorDto)));
    }

    public void deleteById(Long idContributor) {
        existsById(idContributor);
        contributorRepository.deleteById(idContributor);
    }

}
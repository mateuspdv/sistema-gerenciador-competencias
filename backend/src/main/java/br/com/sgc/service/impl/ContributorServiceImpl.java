package br.com.sgc.service.impl;

import br.com.sgc.model.CompetencyModel;
import br.com.sgc.model.ContributorCompetencyModel;
import br.com.sgc.model.ContributorModel;
import br.com.sgc.model.LevelCompetencyModel;
import br.com.sgc.model.pk.ContributorCompetencyPk;
import br.com.sgc.repository.CompetencyRepository;
import br.com.sgc.repository.ContributorCompetencyRepository;
import br.com.sgc.repository.ContributorRepository;
import br.com.sgc.repository.LevelCompetencyRepository;
import br.com.sgc.service.ContributorService;
import br.com.sgc.service.SeniorityService;
import br.com.sgc.service.dto.ContributorCompetencyDto;
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

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class ContributorServiceImpl implements ContributorService {

    private final SeniorityService seniorityService;

    private final ContributorRepository contributorRepository;

    private final ContributorCompetencyRepository contributorCompetencyRepository;

    private final CompetencyRepository competencyRepository;

    private final LevelCompetencyRepository levelCompetencyRepository;

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

    private void setRelatedCompetency(ContributorCompetencyModel contributorCompetencyModel, Long idCompetency) {
        Optional<CompetencyModel> competencyModel = competencyRepository.findById(idCompetency);
        if (competencyModel.isPresent()) {
            contributorCompetencyModel.setCompetencyModel(competencyModel.get());
            return;
        }
        throw new EntityNotFoundException(MessageUtil.COMPETENCY_NOT_FOUND);
    }

    private void setRelatedLevelCompetency(ContributorCompetencyModel contributorCompetencyModel, Long idLevelCompetency) {
        Optional<LevelCompetencyModel> levelCompetencyModel = levelCompetencyRepository.findById(idLevelCompetency);
        if (levelCompetencyModel.isPresent()) {
            contributorCompetencyModel.setLevelCompetencyModel(levelCompetencyModel.get());
            return;
        }
        throw new EntityNotFoundException(MessageUtil.LEVEL_COMPETENCY_NOT_FOUND);
    }

    private void createAndSetPrimaryKey(ContributorCompetencyModel contributorCompetencyModel,
                                        Long idContributor, Long idCompetency,
                                        Long idLevelCompetency) {
        ContributorCompetencyPk contributorCompetencyPk = new ContributorCompetencyPk();
        contributorCompetencyPk.setIdContributor(idContributor);
        contributorCompetencyPk.setIdCompetency(idCompetency);
        contributorCompetencyPk.setIdLevelCompetency(idLevelCompetency);
        contributorCompetencyModel.setId(contributorCompetencyPk);
    }

    private void persistInContributorCompetency(ContributorModel contributorModel, Set<ContributorCompetencyDto> competencies) {
        competencies.forEach(competency -> {
            ContributorCompetencyModel contributorCompetencyModel = new ContributorCompetencyModel();
            contributorCompetencyModel.setContributorModel(contributorModel);
            setRelatedCompetency(contributorCompetencyModel, competency.getIdCompetency());
            setRelatedLevelCompetency(contributorCompetencyModel, competency.getIdLevelCompetency());
            createAndSetPrimaryKey(contributorCompetencyModel, contributorModel.getId(),
                    competency.getIdCompetency(),
                    competency.getIdLevelCompetency());
            contributorCompetencyRepository.save(contributorCompetencyModel);
        });
    }

    public ContributorDto create(ContributorDto contributorDto) {
        seniorityService.existsById(contributorDto.getIdSeniority());
        ContributorModel contributorModel = contributorRepository.saveAndFlush(contributorMapper.toEntity(contributorDto));
        persistInContributorCompetency(contributorModel, contributorDto.getCompetencies());
        return contributorMapper.toDto(contributorModel);
    }

    public ContributorDto update(ContributorDto contributorDto) {
        existsById(contributorDto.getId());
        seniorityService.existsById(contributorDto.getIdSeniority());
        ContributorModel contributorModel = contributorRepository.saveAndFlush(contributorMapper.toEntity(contributorDto));
        contributorCompetencyRepository.deleteAllRelatedCompetenciesByIdContributor(contributorDto.getId());
        persistInContributorCompetency(contributorModel, contributorDto.getCompetencies());
        return contributorMapper.toDto(contributorRepository.save(contributorMapper.toEntity(contributorDto)));
    }

    public void deleteById(Long idContributor) {
        existsById(idContributor);
        contributorRepository.deleteById(idContributor);
    }

}

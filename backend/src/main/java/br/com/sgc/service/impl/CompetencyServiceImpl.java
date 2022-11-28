package br.com.sgc.service.impl;

import br.com.sgc.repository.CompetencyRepository;
import br.com.sgc.service.CategoryService;
import br.com.sgc.service.CompetencyService;
import br.com.sgc.service.dto.CompetencyDto;
import br.com.sgc.service.exception.EntityNotFoundException;
import br.com.sgc.service.mapper.CompetencyMapper;
import br.com.sgc.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CompetencyServiceImpl implements CompetencyService {

    private final CategoryService categoryService;

    private final CompetencyRepository competencyRepository;

    private final CompetencyMapper competencyMapper;

    public Page<CompetencyDto> findAll(Pageable pageable) {
        return competencyRepository.findAll(pageable).map(competencyMapper::toDto);
    }

    public Page<CompetencyDto> globalSearchFilter(Pageable pageable, String query) {
        return competencyRepository.globalSearchFilter(pageable, query);
    }

    public CompetencyDto findById(Long idCompetency) {
        return competencyMapper.toDto(competencyRepository.findById(idCompetency)
                .orElseThrow(() -> new EntityNotFoundException(MessageUtil.COMPETENCY_NOT_FOUND)));
    }

    private void existsCompetencyById(Long idCompetency) {
        if (!competencyRepository.existsById(idCompetency)) {
            throw new EntityNotFoundException(MessageUtil.COMPETENCY_NOT_FOUND);
        }
    }

    public CompetencyDto create(CompetencyDto competencyDto) {
        categoryService.existsById(competencyDto.getIdCategory());
        return competencyMapper.toDto(competencyRepository.save(competencyMapper.toEntity(competencyDto)));
    }

    public CompetencyDto update(CompetencyDto competencyDto) {
        existsCompetencyById(competencyDto.getId());
        categoryService.existsById(competencyDto.getIdCategory());
        return competencyMapper.toDto(competencyRepository.save(competencyMapper.toEntity(competencyDto)));
    }

    public void deleteById(Long idCompetency) {
        existsCompetencyById(idCompetency);
        competencyRepository.deleteById(idCompetency);
    }

}

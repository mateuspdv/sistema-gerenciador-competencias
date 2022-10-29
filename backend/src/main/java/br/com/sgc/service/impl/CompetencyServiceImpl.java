package br.com.sgc.service.impl;

import br.com.sgc.repository.CompetencyRepository;
import br.com.sgc.service.CategoryService;
import br.com.sgc.service.CompetencyService;
import br.com.sgc.service.dto.CompetencyDto;
import br.com.sgc.service.exception.EntityNotFoundException;
import br.com.sgc.service.mapper.CompetencyMapper;
import br.com.sgc.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CompetencyServiceImpl implements CompetencyService {

    private final CategoryService categoryService;

    private final CompetencyRepository competencyRepository;

    private final CompetencyMapper competencyMapper;

    public List<CompetencyDto> findAll() {
        return competencyMapper.toDto(competencyRepository.findAll());
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

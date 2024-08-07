package br.com.sgc.service.impl;

import br.com.sgc.domain.CompetencyLevel;
import br.com.sgc.repository.CompetencyLevelRepository;
import br.com.sgc.service.CompetencyLevelService;
import br.com.sgc.service.dto.CompetencyLevelDto;
import br.com.sgc.service.dto.DropdownDto;
import br.com.sgc.service.mapper.CompetencyLevelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompetencyLevelServiceImpl implements CompetencyLevelService {

    private final CompetencyLevelRepository competencyLevelRepository;

    private final CompetencyLevelMapper competencyLevelMapper;

    public List<DropdownDto> findAll() {
        return competencyLevelRepository.findAll().stream().map(competencyLevel ->
                new DropdownDto(competencyLevel.getDescription(), competencyLevel.getId())).toList();
    }

    public CompetencyLevelDto findById(Long id) {
        return competencyLevelMapper.toDto(competencyLevelRepository.findById(id)
                .orElseThrow(RuntimeException::new));
    }

    public CompetencyLevel findEntityById(Long id) {
        return competencyLevelRepository.findById(id).orElseThrow(RuntimeException::new);
    }

}

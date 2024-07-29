package br.com.sgc.service.impl;

import br.com.sgc.repository.CompetencyRepository;
import br.com.sgc.service.CompetencyService;
import br.com.sgc.service.dto.CompetencyDto;
import br.com.sgc.service.mapper.CompetencyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompetencyServiceImpl implements CompetencyService {

    private final CompetencyRepository competencyRepository;

    private final CompetencyMapper competencyMapper;

    public Page<CompetencyDto> findAll(Pageable pageable) {
        return competencyRepository.findAll(pageable).map(competencyMapper::toDto);
    }

    public CompetencyDto findById(Long id) {
        return competencyMapper.toDto(competencyRepository.findById(id)
                .orElseThrow(RuntimeException::new));
    }

    public CompetencyDto create(CompetencyDto competencyDto) {
        competencyDto.setActive(Boolean.TRUE);
        return competencyMapper.toDto(competencyRepository.save(competencyMapper.toEntity(competencyDto)));
    }

    private void existsById(Long id) {
        if (!competencyRepository.existsById(id)) {
            throw new RuntimeException();
        }
    }

    private void isActiveById(Long id) {
        if (!competencyRepository.findActiveById(id)) {
            throw new RuntimeException();
        }
    }

    public CompetencyDto update(CompetencyDto competencyDto) {
        existsById(competencyDto.getId());
        isActiveById(competencyDto.getId());
        competencyDto.setActive(Boolean.TRUE);
        return competencyMapper.toDto(competencyRepository.save(competencyMapper.toEntity(competencyDto)));
    }

    public void disableById(Long id) {
        CompetencyDto competencyDto = findById(id);
        competencyDto.setActive(Boolean.FALSE);
        competencyRepository.save(competencyMapper.toEntity(competencyDto));
    }

}

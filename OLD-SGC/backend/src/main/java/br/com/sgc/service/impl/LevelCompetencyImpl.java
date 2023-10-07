package br.com.sgc.service.impl;

import br.com.sgc.repository.LevelCompetencyRepository;
import br.com.sgc.service.LevelCompetencyService;
import br.com.sgc.service.dto.LevelCompetencyDto;
import br.com.sgc.service.exception.EntityNotFoundException;
import br.com.sgc.service.mapper.LevelCompetencyMapper;
import br.com.sgc.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LevelCompetencyImpl implements LevelCompetencyService {

    private final LevelCompetencyRepository levelCompetencyRepository;

    private final LevelCompetencyMapper levelCompetencyMapper;

    public List<LevelCompetencyDto> findAll() {
        return levelCompetencyMapper.toDto(levelCompetencyRepository.findAll());
    }

    public LevelCompetencyDto findById(Long idLevelCompetency) {
        return levelCompetencyMapper.toDto(levelCompetencyRepository.findById(idLevelCompetency)
                .orElseThrow(() -> new EntityNotFoundException(MessageUtil.LEVEL_COMPETENCY_NOT_FOUND)));
    }

}

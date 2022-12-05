package br.com.sgc.service.impl;

import br.com.sgc.repository.SeniorityRepository;
import br.com.sgc.service.SeniorityService;
import br.com.sgc.service.dto.SeniorityDto;
import br.com.sgc.service.exception.EntityNotFoundException;
import br.com.sgc.service.mapper.SeniorityMapper;
import br.com.sgc.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeniorityServiceImpl implements SeniorityService {

    private final SeniorityRepository seniorityRepository;

    private final SeniorityMapper seniorityMapper;

    public List<SeniorityDto> findAll() {
        return seniorityMapper.toDto(seniorityRepository.findAll());
    }

    public SeniorityDto findById(Long idSeniority) {
        return seniorityMapper.toDto(seniorityRepository.findById(idSeniority)
                .orElseThrow(() -> new EntityNotFoundException(MessageUtil.SENIORITY_NOT_FOUND)));
    }

}

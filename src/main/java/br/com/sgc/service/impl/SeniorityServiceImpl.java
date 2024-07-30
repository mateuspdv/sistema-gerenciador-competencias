package br.com.sgc.service.impl;

import br.com.sgc.repository.SeniorityRepository;
import br.com.sgc.service.SeniorityService;
import br.com.sgc.service.dto.DropdownDto;
import br.com.sgc.service.dto.SeniorityDto;
import br.com.sgc.service.mapper.SeniorityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeniorityServiceImpl implements SeniorityService {

    private final SeniorityRepository seniorityRepository;

    private final SeniorityMapper seniorityMapper;

    public List<DropdownDto> findAll() {
        return seniorityRepository.findAll().stream().map(category ->
                new DropdownDto(category.getDescription(), category.getId())).toList();
    }

    public SeniorityDto findById(Long id) {
        return seniorityMapper.toDto(seniorityRepository.findById(id)
                .orElseThrow(RuntimeException::new));
    }

}

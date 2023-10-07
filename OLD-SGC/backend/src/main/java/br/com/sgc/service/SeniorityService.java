package br.com.sgc.service;

import br.com.sgc.service.dto.SeniorityDto;

import java.util.List;

public interface SeniorityService {

    List<SeniorityDto> findAll();

    SeniorityDto findById(Long idSeniority);

    void existsById(Long idSeniority);

}

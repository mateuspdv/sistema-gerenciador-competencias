package br.com.sgc.service;

import br.com.sgc.service.dto.DropdownDto;
import br.com.sgc.service.dto.SeniorityDto;

import java.util.List;

public interface SeniorityService {

    List<DropdownDto> findAll();

    SeniorityDto findById(Long id);

}

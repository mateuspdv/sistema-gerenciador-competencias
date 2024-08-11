package br.com.sgc.service;

import br.com.sgc.service.dto.DropdownDto;
import br.com.sgc.service.dto.TrainingClassStatusDto;

import java.util.List;

public interface TrainingClassStatusService {

    List<DropdownDto> findAll();

    TrainingClassStatusDto findById(Long id);

}

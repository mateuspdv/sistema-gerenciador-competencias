package br.com.sgc.service;

import br.com.sgc.service.dto.TrainingClassDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TrainingClassService {

    Page<TrainingClassDto> findAll(Pageable pageable);

    TrainingClassDto findById(Long id);

    TrainingClassDto create(TrainingClassDto dto);

    TrainingClassDto update(TrainingClassDto dto);

    void disableById(Long id);

}

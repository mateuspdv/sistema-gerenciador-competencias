package br.com.sgc.service.impl;

import br.com.sgc.repository.TrainingClassStatusRepository;
import br.com.sgc.service.TrainingClassStatusService;
import br.com.sgc.service.dto.DropdownDto;
import br.com.sgc.service.dto.TrainingClassStatusDto;
import br.com.sgc.service.mapper.TrainingClassStatusMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingClassStatusServiceImpl implements TrainingClassStatusService {

    private final TrainingClassStatusRepository trainingClassStatusRepository;

    private final TrainingClassStatusMapper trainingClassStatusMapper;

    public List<DropdownDto> findAll() {
        return trainingClassStatusRepository.findAll().stream().map(status ->
                new DropdownDto(status.getDescription(), status.getId())).toList();
    }

    public TrainingClassStatusDto findById(Long id) {
        return trainingClassStatusMapper.toDto(trainingClassStatusRepository.findById(id)
                .orElseThrow(RuntimeException::new));
    }

}

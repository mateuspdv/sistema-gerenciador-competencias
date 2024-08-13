package br.com.sgc.service.impl;

import br.com.sgc.domain.TrainingClass;
import br.com.sgc.domain.TrainingClassEmployeeCompetency;
import br.com.sgc.domain.pk.TrainingClassEmployeeCompetencyPk;
import br.com.sgc.repository.TrainingClassRepository;
import br.com.sgc.service.CompetencyService;
import br.com.sgc.service.EmployeeService;
import br.com.sgc.service.TrainingClassService;
import br.com.sgc.service.dto.TrainingClassDto;
import br.com.sgc.service.dto.TrainingClassEmployeeCompetencyDto;
import br.com.sgc.service.mapper.TrainingClassMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TrainingClassServiceImpl implements TrainingClassService {

    private final TrainingClassRepository trainingClassRepository;

    private final TrainingClassMapper trainingClassMapper;

    private final EmployeeService employeeService;

    private final CompetencyService competencyService;

    public Page<TrainingClassDto> findAll(Pageable pageable) {
        return trainingClassRepository.findAll(pageable).map(trainingClassMapper::toDto);
    }

    public TrainingClassDto findById(Long id) {
        return trainingClassMapper.toDto(trainingClassRepository.findById(id)
                .orElseThrow(RuntimeException::new));
    }

    private void setupTrainingClassEmployeeCompetency(TrainingClass trainingClass, Set<TrainingClassEmployeeCompetencyDto> employees) {
        Set<TrainingClassEmployeeCompetency> trainingClassEmployeeCompetencies = new HashSet<>();

        employees.forEach(employee -> {
            TrainingClassEmployeeCompetency trainingClassEmployeeCompetency = new TrainingClassEmployeeCompetency();

            TrainingClassEmployeeCompetencyPk pk = new TrainingClassEmployeeCompetencyPk();
            pk.setIdTrainingClass(trainingClass.getId());
            pk.setIdEmployee(employee.getIdEmployee());
            pk.setIdCompetency(employee.getIdCompetency());
            trainingClassEmployeeCompetency.setId(pk);

            trainingClassEmployeeCompetency.setTrainingClass(trainingClass);
            trainingClassEmployeeCompetency.setEmployee(employeeService.findEntityById(employee.getIdEmployee()));
            trainingClassEmployeeCompetency.setCompetency(competencyService.findEntityById(employee.getIdCompetency()));

            trainingClassEmployeeCompetencies.add(trainingClassEmployeeCompetency);
        });

        trainingClass.getEmployees().addAll(trainingClassEmployeeCompetencies);
    }

    public TrainingClassDto create(TrainingClassDto dto) {
        dto.setActive(Boolean.TRUE);
        TrainingClass trainingClass = trainingClassMapper.toEntity(dto);
        setupTrainingClassEmployeeCompetency(trainingClass, dto.getEmployees());
        return trainingClassMapper.toDto(trainingClassRepository.save(trainingClass));
    }

    private void existsById(Long id) {
        if (!trainingClassRepository.existsById(id)) {
            throw new RuntimeException();
        }
    }

    private void isActiveById(Long id) {
        if (!trainingClassRepository.findActiveById(id)) {
            throw new RuntimeException();
        }
    }

    public TrainingClassDto update(TrainingClassDto dto) {
        existsById(dto.getId());
        isActiveById(dto.getId());
        dto.setActive(Boolean.TRUE);
        TrainingClass trainingClass = trainingClassMapper.toEntity(dto);
        setupTrainingClassEmployeeCompetency(trainingClass, dto.getEmployees());
        return trainingClassMapper.toDto(trainingClassRepository.save(trainingClass));
    }

    public void disableById(Long id) {
        TrainingClassDto dto = findById(id);
        dto.setActive(Boolean.FALSE);
        trainingClassRepository.save(trainingClassMapper.toEntity(dto));
    }

}

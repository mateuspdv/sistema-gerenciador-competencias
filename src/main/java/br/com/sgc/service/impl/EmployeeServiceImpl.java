package br.com.sgc.service.impl;

import br.com.sgc.domain.Employee;
import br.com.sgc.domain.EmployeeCompetency;
import br.com.sgc.domain.pk.EmployeeCompetencyPk;
import br.com.sgc.repository.EmployeeRepository;
import br.com.sgc.service.CompetencyLevelService;
import br.com.sgc.service.CompetencyService;
import br.com.sgc.service.EmployeeService;
import br.com.sgc.service.dto.EmployeeCompetencyDto;
import br.com.sgc.service.dto.EmployeeDto;
import br.com.sgc.service.mapper.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    private final CompetencyService competencyService;

    private final CompetencyLevelService competencyLevelService;

    public Page<EmployeeDto> findAll(Pageable pageable) {
        return employeeRepository.findAll(pageable).map(employeeMapper::toDto);
    }

    public EmployeeDto findById(Long id) {
        return employeeMapper.toDto(employeeRepository.findById(id)
                .orElseThrow(RuntimeException::new));
    }

    public Employee findEntityById(Long id) {
        return employeeRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    private void setupEmployeeCompetencies(Employee employee, List<EmployeeCompetencyDto> competencies) {
        List<EmployeeCompetency> employeeCompetencies = new ArrayList<>();

        competencies.forEach(competency -> {
            EmployeeCompetency employeeCompetency = new EmployeeCompetency();

            EmployeeCompetencyPk pk = new EmployeeCompetencyPk();
            pk.setIdEmployee(employee.getId());
            pk.setIdCompetency(competency.getIdCompetency());
            employeeCompetency.setId(pk);

            employeeCompetency.setEmployee(employee);
            employeeCompetency.setCompetency(competencyService.findEntityById(competency.getIdCompetency()));
            employeeCompetency.setCompetencyLevel(competencyLevelService.findEntityById(competency.getIdCompetencyLevel()));

            employeeCompetencies.add(employeeCompetency);
        });

        employee.getCompetencies().addAll(employeeCompetencies);
    }

    public EmployeeDto create(EmployeeDto employeeDto) {
        employeeDto.setActive(Boolean.TRUE);
        Employee employee = employeeMapper.toEntity(employeeDto);
        setupEmployeeCompetencies(employee, employeeDto.getCompetencies());
        return employeeMapper.toDto(employeeRepository.save(employee));
    }

    private void existsById(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException();
        }
    }

    private void isActiveById(Long id) {
        if (!employeeRepository.findActiveById(id)) {
            throw new RuntimeException();
        }
    }

    public EmployeeDto update(EmployeeDto employeeDto) {
        existsById(employeeDto.getId());
        isActiveById(employeeDto.getId());
        employeeDto.setActive(Boolean.TRUE);
        Employee employee = employeeMapper.toEntity(employeeDto);
        setupEmployeeCompetencies(employee, employeeDto.getCompetencies());
        return employeeMapper.toDto(employeeRepository.save(employee));
    }

    public void disableById(Long id) {
        EmployeeDto employeeDto = findById(id);
        employeeDto.setActive(Boolean.FALSE);
        employeeRepository.save(employeeMapper.toEntity(employeeDto));
    }

}

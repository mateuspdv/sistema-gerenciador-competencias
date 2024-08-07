package br.com.sgc.service;

import br.com.sgc.service.dto.EmployeeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {

    Page<EmployeeDto> findAll(Pageable pageable);

    EmployeeDto findById(Long id);

    EmployeeDto create(EmployeeDto employeeDto);

    EmployeeDto update(EmployeeDto employeeDto);

    void disableById(Long id);

}

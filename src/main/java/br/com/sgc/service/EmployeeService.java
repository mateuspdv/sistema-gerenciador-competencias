package br.com.sgc.service;

import br.com.sgc.domain.Employee;
import br.com.sgc.service.dto.EmployeeDto;
import br.com.sgc.service.filter.EmployeeFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {

    Page<EmployeeDto> filter(EmployeeFilter filter, Pageable pageable);

    EmployeeDto findById(Long id);

    Employee findEntityById(Long id);

    EmployeeDto create(EmployeeDto employeeDto);

    EmployeeDto update(EmployeeDto employeeDto);

    void disableById(Long id);

}

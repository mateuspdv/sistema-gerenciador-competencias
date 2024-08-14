package br.com.sgc.controller;

import br.com.sgc.service.EmployeeService;
import br.com.sgc.service.dto.EmployeeDto;
import br.com.sgc.service.filter.EmployeeFilter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<Page<EmployeeDto>> filter(@RequestBody EmployeeFilter filter, Pageable pageable) {
        return ResponseEntity.ok(employeeService.filter(filter, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> create(@Valid @RequestBody EmployeeDto employeeDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.create(employeeDto));
    }

    @PutMapping
    public ResponseEntity<EmployeeDto> update(@Valid @RequestBody EmployeeDto employeeDto) {
        return ResponseEntity.ok(employeeService.update(employeeDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> disableById(@PathVariable Long id) {
        employeeService.disableById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

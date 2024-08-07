package br.com.sgc.service.mapper;

import br.com.sgc.domain.Employee;
import br.com.sgc.service.dto.EmployeeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {EmployeeCompetencyMapper.class})
public interface EmployeeMapper extends EntityMapper<EmployeeDto, Employee> {

    @Mapping(source = "seniority.id", target = "idSeniority")
    EmployeeDto toDto(Employee entity);

    @Mapping(source = "idSeniority", target = "seniority.id")
    @Mapping(target = "competencies", ignore = true)
    Employee toEntity(EmployeeDto dto);

}

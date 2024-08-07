package br.com.sgc.service.mapper;

import br.com.sgc.domain.EmployeeCompetency;
import br.com.sgc.service.dto.EmployeeCompetencyDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeCompetencyMapper extends EntityMapper<EmployeeCompetencyDto, EmployeeCompetency> {

    @Mapping(source = "competency.id", target = "idCompetency")
    @Mapping(source = "competencyLevel.id", target = "idCompetencyLevel")
    EmployeeCompetencyDto toDto(EmployeeCompetency entity);

    @Mapping(source = "idCompetency", target = "competency.id")
    @Mapping(source = "idCompetencyLevel", target = "competencyLevel.id")
    EmployeeCompetency toEntity(EmployeeCompetencyDto dto);

}

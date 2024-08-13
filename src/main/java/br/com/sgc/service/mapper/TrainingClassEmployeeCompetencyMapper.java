package br.com.sgc.service.mapper;

import br.com.sgc.domain.TrainingClassEmployeeCompetency;
import br.com.sgc.service.dto.TrainingClassEmployeeCompetencyDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TrainingClassEmployeeCompetencyMapper extends EntityMapper<TrainingClassEmployeeCompetencyDto, TrainingClassEmployeeCompetency> {

    @Mapping(source = "employee.id", target = "idEmployee")
    @Mapping(source = "competency.id", target = "idCompetency")
    TrainingClassEmployeeCompetencyDto toDto(TrainingClassEmployeeCompetency entity);

    @Mapping(source = "idEmployee", target = "employee.id")
    @Mapping(source = "idCompetency", target = "competency.id")
    TrainingClassEmployeeCompetency toEntity(TrainingClassEmployeeCompetencyDto dto);

}

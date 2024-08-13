package br.com.sgc.service.mapper;

import br.com.sgc.domain.TrainingClass;
import br.com.sgc.service.dto.TrainingClassDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TrainingClassEmployeeCompetencyMapper.class})
public interface TrainingClassMapper extends EntityMapper<TrainingClassDto, TrainingClass> {

    @Mapping(source = "trainingClassStatus.id", target = "idTrainingClassStatus")
    TrainingClassDto toDto(TrainingClass entity);

    @Mapping(source = "idTrainingClassStatus", target = "trainingClassStatus.id")
    @Mapping(target = "employees", ignore = true)
    TrainingClass toEntity(TrainingClassDto dto);

}

package br.com.sgc.service.mapper;

import br.com.sgc.domain.TrainingClassStatus;
import br.com.sgc.service.dto.TrainingClassStatusDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrainingClassStatusMapper extends EntityMapper<TrainingClassStatusDto, TrainingClassStatus> {
}

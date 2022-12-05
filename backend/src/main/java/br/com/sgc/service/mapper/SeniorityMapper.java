package br.com.sgc.service.mapper;

import br.com.sgc.model.SeniorityModel;
import br.com.sgc.service.dto.SeniorityDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SeniorityMapper extends EntityMapper<SeniorityDto, SeniorityModel> {
}

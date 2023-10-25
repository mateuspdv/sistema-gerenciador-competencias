package br.com.coresgc.service.mapper;

import br.com.coresgc.domain.Competency;
import br.com.coresgc.domain.Contributor;
import br.com.coresgc.domain.Seniority;
import br.com.coresgc.service.dto.CompetencyDTO;
import br.com.coresgc.service.dto.ContributorDTO;
import br.com.coresgc.service.dto.SeniorityDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper for the entity {@link Contributor} and its DTO {@link ContributorDTO}.
 */
@Mapper(componentModel = "spring")
public interface ContributorMapper extends EntityMapper<ContributorDTO, Contributor> {
    @Mapping(target = "seniority", source = "seniority", qualifiedByName = "toDtoSeniority")
    @Mapping(target = "competences", source = "competences", qualifiedByName = "competencyDtoSet")
    ContributorDTO toDto(Contributor s);

    @Mapping(target = "seniority", source = "seniority", qualifiedByName = "toEntitySeniority")
    @Mapping(target = "competences", source = "competences", qualifiedByName = "competencyEntitySet")
    Contributor toEntity(ContributorDTO contributorDTO);

    @Named("toDtoSeniority")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "description", source = "description")
    SeniorityDTO toDtoSeniority(Seniority seniority);

    @Named("toEntitySeniority")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "description", source = "description")
    Seniority toEntitySeniority(SeniorityDTO seniorityDTO);

    @Named("toDtoCompetency")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CompetencyDTO toDtoCompetency(Competency competency);

    @Named("toEntityCompetency")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    Competency toEntityCompetency(CompetencyDTO competencyDTO);

    @Named("competencyDtoSet")
    default Set<CompetencyDTO> competencyDtoSet(Set<Competency> competency) {
        return competency.stream().map(this::toDtoCompetency).collect(Collectors.toSet());
    }

    @Named("competencyEntitySet")
    default Set<Competency> competencyEntitySet(Set<CompetencyDTO> competencyDTO) {
        return competencyDTO.stream().map(this::toEntityCompetency).collect(Collectors.toSet());
    }

}

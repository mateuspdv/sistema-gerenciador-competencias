package br.com.sgc.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainingClassDto implements Serializable {

    private Long id;

    private String name;

    private String description;

    private LocalDate dateStart;

    private LocalDate dateEnd;

    private Boolean active;

    private Long idTrainingClassStatus;

    private Set<TrainingClassEmployeeCompetencyDto> employees = new HashSet<>();

}

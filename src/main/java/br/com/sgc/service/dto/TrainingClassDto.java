package br.com.sgc.service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "O campo nome não pode ser nulo!")
    @NotEmpty(message = "O campo nome não pode ser vazio!")
    @Size(min = 3, message = "O campo nome deve possuir no mínimo 3 caracteres!")
    @Size(max = 50, message = "O campo nome deve possuir no máximo 50 caracteres!")
    private String name;

    @NotNull(message = "O campo descrição não pode ser nulo!")
    @NotEmpty(message = "O campo descrição não pode ser vazio!")
    @Size(min = 3, message = "O campo descrição deve possuir no mínimo 3 caracteres!")
    @Size(max = 255, message = "O campo descrição deve possuir no máximo 255 caracteres!")
    private String description;

    @NotNull(message = "O campo data de início não pode ser nulo!")
    private LocalDate dateStart;

    @NotNull(message = "O campo data de fim não pode ser nulo!")
    private LocalDate dateEnd;

    private Boolean active;

    @NotNull(message = "O campo status não pode ser nulo!")
    private Long idTrainingClassStatus;

    private Set<TrainingClassEmployeeCompetencyDto> employees = new HashSet<>();

}

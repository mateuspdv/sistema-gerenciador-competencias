package br.com.sgc.service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompetencyDto implements Serializable {

    private Long id;

    @NotNull(message = "O campo nome não pode ser vazio!")
    @Size(min = 3, message = "O campo nome deve possuir no mínimo 3 caracteres!")
    @Size(max = 50, message = "O campo nome deve possuir no máximo 50 caracteres!")
    private String name;

    @NotNull(message = "O campo descrição não pode ser vazio!")
    @Size(min = 3, message = "O campo descrição deve possuir no mínimo 3 caracteres!")
    @Size(max = 100, message = "O campo descrição deve possuir no máximo 100 caracteres!")
    private String description;

    private Boolean active;

    @NotNull(message = "O campo categoria não pode ser nulo!")
    private Long idCategory;

}

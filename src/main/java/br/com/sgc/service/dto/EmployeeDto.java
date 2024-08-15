package br.com.sgc.service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto implements Serializable {

    private Long id;

    @NotNull(message = "O campo nome não pode ser nulo!")
    @NotEmpty(message = "O campo nome não pode ser vazio!")
    @Size(min = 3, message = "O campo nome deve possuir no mínimo 3 caracteres!")
    @Size(max = 50, message = "O campo nome deve possuir no máximo 50 caracteres!")
    private String name;

    @NotNull(message = "O campo sobrenome não pode ser nulo!")
    @NotEmpty(message = "O campo sobrenome não pode ser vazio!")
    @Size(min = 3, message = "O campo sobrenome deve possuir no mínimo 3 caracteres!")
    @Size(max = 50, message = "O campo sobrenome deve possuir no máximo 50 caracteres!")
    private String lastName;

    @NotNull(message = "O campo CPF não pode ser nulo!")
    @CPF(message = "O campo CPF deve possuir um registro válido!")
    private String cpf;

    @NotNull(message = "O campo e-mail não pode ser nulo!")
    @NotEmpty(message = "O campo e-mail não pode ser vazio!")
    @Size(max = 100, message = "O campo e-mail deve possuir no máximo 100 caracteres!")
    @Email(message = "o campo e-mail deve possuir um registro válido!")
    private String email;

    @NotNull(message = "O campo data de nascimento não pode ser nulo!")
    private LocalDate dateBirth;

    @NotNull(message = "O campo data de admissão não pode ser nulo!")
    private LocalDate dateHire;

    private Boolean active;

    @NotNull(message = "O campo senioridade não pode ser nulo!")
    private Long idSeniority;

    private List<EmployeeCompetencyDto> competencies = new ArrayList<>();

}

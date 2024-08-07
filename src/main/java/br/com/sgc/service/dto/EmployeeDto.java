package br.com.sgc.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private String name;

    private String lastName;

    private String cpf;

    private String email;

    private LocalDate dateBirth;

    private LocalDate dateHire;

    private Boolean active;

    private Long idSeniority;

    private List<EmployeeCompetencyDto> competencies = new ArrayList<>();

}

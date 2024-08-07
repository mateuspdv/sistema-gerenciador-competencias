package br.com.sgc.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCompetencyDto implements Serializable {

    private Long idCompetency;

    private Long idCompetencyLevel;

}

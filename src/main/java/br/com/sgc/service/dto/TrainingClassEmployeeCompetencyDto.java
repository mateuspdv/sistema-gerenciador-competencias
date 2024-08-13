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
public class TrainingClassEmployeeCompetencyDto implements Serializable {

    private Long idEmployee;

    private Long idCompetency;

}

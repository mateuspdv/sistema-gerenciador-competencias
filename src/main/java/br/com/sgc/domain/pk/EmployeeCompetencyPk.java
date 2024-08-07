package br.com.sgc.domain.pk;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCompetencyPk implements Serializable {

    private Long idEmployee;

    private Long idCompetency;

}

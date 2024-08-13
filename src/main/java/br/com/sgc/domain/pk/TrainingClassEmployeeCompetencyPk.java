package br.com.sgc.domain.pk;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainingClassEmployeeCompetencyPk {

    private Long idTrainingClass;

    private Long idEmployee;

    private Long idCompetency;

}

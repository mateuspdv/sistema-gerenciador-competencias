package br.com.sgc.domain;

import br.com.sgc.domain.pk.TrainingClassEmployeeCompetencyPk;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "training_class_employee_competency")
@Getter
@Setter
public class TrainingClassEmployeeCompetency implements Serializable {

    @EmbeddedId
    private TrainingClassEmployeeCompetencyPk id;

    @ManyToOne
    @MapsId("idTrainingClass")
    @JoinColumn(name = "id_training_class", referencedColumnName = "id", nullable = false)
    private TrainingClass trainingClass;

    @ManyToOne
    @MapsId("idEmployee")
    @JoinColumn(name = "id_employee", referencedColumnName = "id", nullable = false)
    private Employee employee;

    @ManyToOne
    @MapsId("idCompetency")
    @JoinColumn(name = "id_competency", referencedColumnName = "id", nullable = false)
    private Competency competency;

}

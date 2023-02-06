package br.com.sgc.model.pk;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContributorCompetencyPk implements Serializable {

    @Column(name = "id_contributor", nullable = false)
    private Long idContributor;

    @Column(name = "id_competency", nullable = false)
    private Long idCompetency;

    @Column(name = "id_level_competency", nullable = false)
    private Long idLevelCompetency;

}

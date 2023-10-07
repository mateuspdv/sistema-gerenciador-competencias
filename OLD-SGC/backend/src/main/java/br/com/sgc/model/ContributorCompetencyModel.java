package br.com.sgc.model;

import br.com.sgc.model.pk.ContributorCompetencyPk;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "contributor_competency")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContributorCompetencyModel implements Serializable {

    @EmbeddedId
    private ContributorCompetencyPk id;

    @ManyToOne
    @MapsId("idContributor")
    @JoinColumn(name = "id_contributor", referencedColumnName = "id")
    private ContributorModel contributorModel;

    @ManyToOne
    @MapsId("idCompetency")
    @JoinColumn(name = "id_competency", referencedColumnName = "id")
    private CompetencyModel competencyModel;

    @ManyToOne
    @MapsId("idLevelCompetency")
    @JoinColumn(name = "id_level_competency", referencedColumnName = "id")
    private LevelCompetencyModel levelCompetencyModel;

}

package br.com.sgc.repository;

import br.com.sgc.model.ContributorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContributorRepository extends JpaRepository<ContributorModel, Long> {

    @Query("SELECT COUNT(CC.contributorModel.id) > 0 " +
            " FROM " +
            " ContributorCompetencyModel CC " +
            " WHERE CC.contributorModel.id = :idContributor ")
    Boolean haveRelationCompetency(@Param("idContributor") Long idContributor);

}

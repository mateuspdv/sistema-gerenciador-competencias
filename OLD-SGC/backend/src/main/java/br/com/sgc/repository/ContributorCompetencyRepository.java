package br.com.sgc.repository;

import br.com.sgc.model.ContributorCompetencyModel;
import br.com.sgc.model.pk.ContributorCompetencyPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface ContributorCompetencyRepository extends JpaRepository<ContributorCompetencyModel, ContributorCompetencyPk> {

    @Transactional
    @Modifying
    @Query("DELETE FROM ContributorCompetencyModel WHERE contributorModel.id = :idContributor")
    void deleteAllRelatedCompetenciesByIdContributor(@Param("idContributor") Long idContributor);

}

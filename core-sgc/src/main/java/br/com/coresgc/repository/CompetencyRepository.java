package br.com.coresgc.repository;

import br.com.coresgc.domain.Competency;
import br.com.coresgc.service.dto.ViewCompetencyDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface CompetencyRepository extends JpaRepository<Competency, Long>, JpaSpecificationExecutor<Competency> {

    @Query("SELECT C.creationDate" +
        " FROM " +
        "   Competency C " +
        " WHERE " +
        "   C.id = :id ")
    ZonedDateTime findCreationDateById(@Param("id") Long id);

    @Query("SELECT NEW br.com.coresgc.service.dto.ViewCompetencyDTO(C.id, " +
        " C.name, " +
        " C.description, " +
        " C.category.name) " +
        " FROM " +
        "   Competency C ")
    List<ViewCompetencyDTO> searchAllViews();

}

package br.com.sgc.repository;

import br.com.sgc.domain.Competency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CompetencyRepository extends JpaRepository<Competency, Long> {

    @Query("SELECT C.active " +
            " FROM " +
            "   Competency C " +
            " WHERE " +
            "   C.id = :id")
    Boolean findActiveById(@Param("id") Long id);

}

package br.com.sgc.repository;

import br.com.sgc.domain.TrainingClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TrainingClassRepository extends JpaRepository<TrainingClass, Long> {

    @Query("SELECT TC.active " +
            " FROM " +
            "   TrainingClass TC " +
            " WHERE " +
            "   TC.id = :id")
    Boolean findActiveById(@Param("id") Long id);

}

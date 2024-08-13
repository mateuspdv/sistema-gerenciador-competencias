package br.com.sgc.repository;

import br.com.sgc.domain.Competency;
import br.com.sgc.service.filter.CompetencyFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CompetencyRepository extends JpaRepository<Competency, Long> {

    @Query("SELECT C " +
            " FROM " +
            "   Competency C " +
            " WHERE " +
            "   (:#{#filter.name} IS NULL OR LOWER(C.name) LIKE LOWER(CONCAT('%', :#{#filter.name}, '%'))) AND " +
            "   (:#{#filter.description} IS NULL OR LOWER(C.description) LIKE LOWER(CONCAT('%', :#{#filter.description}, '%'))) AND " +
            "   (:#{#filter.idCategory} IS NULL OR :#{#filter.idCategory} = C.category.id) AND " +
            "   C.active = TRUE")
    Page<Competency> filter(@Param("filter") CompetencyFilter filter, Pageable pageable);

    @Query("SELECT C.active " +
            " FROM " +
            "   Competency C " +
            " WHERE " +
            "   C.id = :id")
    Boolean findActiveById(@Param("id") Long id);

}

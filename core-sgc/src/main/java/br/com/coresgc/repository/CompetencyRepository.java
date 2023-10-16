package br.com.coresgc.repository;

import br.com.coresgc.domain.Competency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Competency entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompetencyRepository extends JpaRepository<Competency, Long>, JpaSpecificationExecutor<Competency> {
}

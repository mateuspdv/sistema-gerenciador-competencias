package br.com.coresgc.repository;

import br.com.coresgc.domain.Seniority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Seniority entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeniorityRepository extends JpaRepository<Seniority, Long> {
}

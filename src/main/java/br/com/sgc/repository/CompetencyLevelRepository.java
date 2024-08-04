package br.com.sgc.repository;

import br.com.sgc.domain.CompetencyLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetencyLevelRepository extends JpaRepository<CompetencyLevel, Long> {
}

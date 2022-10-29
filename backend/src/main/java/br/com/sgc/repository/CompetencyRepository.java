package br.com.sgc.repository;

import br.com.sgc.model.CompetencyModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetencyRepository extends JpaRepository<CompetencyModel, Long> {
}

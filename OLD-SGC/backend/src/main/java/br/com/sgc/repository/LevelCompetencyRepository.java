package br.com.sgc.repository;

import br.com.sgc.model.LevelCompetencyModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LevelCompetencyRepository extends JpaRepository<LevelCompetencyModel, Long> {
}

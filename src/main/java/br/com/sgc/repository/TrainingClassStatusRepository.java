package br.com.sgc.repository;

import br.com.sgc.domain.TrainingClassStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingClassStatusRepository extends JpaRepository<TrainingClassStatus, Long> {
}

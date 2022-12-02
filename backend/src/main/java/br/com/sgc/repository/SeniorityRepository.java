package br.com.sgc.repository;

import br.com.sgc.model.SeniorityModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeniorityRepository extends JpaRepository<SeniorityModel, Long> {
}

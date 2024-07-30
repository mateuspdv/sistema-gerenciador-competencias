package br.com.sgc.repository;

import br.com.sgc.domain.Seniority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeniorityRepository extends JpaRepository<Seniority, Long> {
}

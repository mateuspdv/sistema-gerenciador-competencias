package br.com.sgc.repository;

import br.com.sgc.model.ContributorModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContributorRepository extends JpaRepository<ContributorModel, Long> {
}

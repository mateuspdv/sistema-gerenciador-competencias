package br.com.coresgc.repository;

import br.com.coresgc.domain.Contributor;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ContributorRepositoryWithBagRelationships {
    Optional<Contributor> fetchBagRelationships(Optional<Contributor> contributor);

    List<Contributor> fetchBagRelationships(List<Contributor> contributors);

    Page<Contributor> fetchBagRelationships(Page<Contributor> contributors);
}

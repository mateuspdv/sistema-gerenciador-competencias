package br.com.coresgc.repository;

import br.com.coresgc.domain.Contributor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class ContributorRepositoryWithBagRelationshipsImpl implements ContributorRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Contributor> fetchBagRelationships(Optional<Contributor> contributor) {
        return contributor.map(this::fetchCompetences);
    }

    @Override
    public Page<Contributor> fetchBagRelationships(Page<Contributor> contributors) {
        return new PageImpl<>(
            fetchBagRelationships(contributors.getContent()),
            contributors.getPageable(),
            contributors.getTotalElements()
        );
    }

    @Override
    public List<Contributor> fetchBagRelationships(List<Contributor> contributors) {
        return Optional.of(contributors).map(this::fetchCompetences).orElse(Collections.emptyList());
    }

    Contributor fetchCompetences(Contributor result) {
        return entityManager
            .createQuery(
                "select contributor from Contributor contributor left join fetch contributor.competences where contributor.id = :id",
                Contributor.class
            )
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<Contributor> fetchCompetences(List<Contributor> contributors) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, contributors.size()).forEach(index -> order.put(contributors.get(index).getId(), index));
        List<Contributor> result = entityManager
            .createQuery(
                "select contributor from Contributor contributor left join fetch contributor.competences where contributor in :contributors",
                Contributor.class
            )
            .setParameter("contributors", contributors)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}

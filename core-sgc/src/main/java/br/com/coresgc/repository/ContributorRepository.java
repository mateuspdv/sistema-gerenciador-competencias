package br.com.coresgc.repository;

import br.com.coresgc.domain.Contributor;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Contributor entity.
 *
 * When extending this class, extend ContributorRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface ContributorRepository
    extends ContributorRepositoryWithBagRelationships, JpaRepository<Contributor, Long>, JpaSpecificationExecutor<Contributor> {
    default Optional<Contributor> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<Contributor> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<Contributor> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select contributor from Contributor contributor left join fetch contributor.seniority",
        countQuery = "select count(contributor) from Contributor contributor"
    )
    Page<Contributor> findAllWithToOneRelationships(Pageable pageable);

    @Query("select contributor from Contributor contributor left join fetch contributor.seniority")
    List<Contributor> findAllWithToOneRelationships();

    @Query("select contributor from Contributor contributor left join fetch contributor.seniority where contributor.id =:id")
    Optional<Contributor> findOneWithToOneRelationships(@Param("id") Long id);
}

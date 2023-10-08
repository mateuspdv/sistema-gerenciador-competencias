package br.com.coresgc.service;

import br.com.coresgc.domain.*; // for static metamodels
import br.com.coresgc.domain.Competency;
import br.com.coresgc.repository.CompetencyRepository;
import br.com.coresgc.service.criteria.CompetencyCriteria;
import br.com.coresgc.service.dto.CompetencyDTO;
import br.com.coresgc.service.mapper.CompetencyMapper;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Competency} entities in the database.
 * The main input is a {@link CompetencyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CompetencyDTO} or a {@link Page} of {@link CompetencyDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CompetencyQueryService extends QueryService<Competency> {

    private final Logger log = LoggerFactory.getLogger(CompetencyQueryService.class);

    private final CompetencyRepository competencyRepository;

    private final CompetencyMapper competencyMapper;

    public CompetencyQueryService(CompetencyRepository competencyRepository, CompetencyMapper competencyMapper) {
        this.competencyRepository = competencyRepository;
        this.competencyMapper = competencyMapper;
    }

    /**
     * Return a {@link List} of {@link CompetencyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CompetencyDTO> findByCriteria(CompetencyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Competency> specification = createSpecification(criteria);
        return competencyMapper.toDto(competencyRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CompetencyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CompetencyDTO> findByCriteria(CompetencyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Competency> specification = createSpecification(criteria);
        return competencyRepository.findAll(specification, page).map(competencyMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CompetencyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Competency> specification = createSpecification(criteria);
        return competencyRepository.count(specification);
    }

    /**
     * Function to convert {@link CompetencyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Competency> createSpecification(CompetencyCriteria criteria) {
        Specification<Competency> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Competency_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Competency_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Competency_.description));
            }
            if (criteria.getCreationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreationDate(), Competency_.creationDate));
            }
            if (criteria.getLastUpdateDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastUpdateDate(), Competency_.lastUpdateDate));
            }
            if (criteria.getCategoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCategoryId(),
                            root -> root.join(Competency_.category, JoinType.LEFT).get(Category_.id)
                        )
                    );
            }
        }
        return specification;
    }
}

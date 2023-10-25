package br.com.coresgc.service;

import br.com.coresgc.domain.*; // for static metamodels
import br.com.coresgc.domain.Contributor;
import br.com.coresgc.repository.ContributorRepository;
import br.com.coresgc.service.criteria.ContributorCriteria;
import br.com.coresgc.service.dto.ContributorDTO;
import br.com.coresgc.service.mapper.ContributorMapper;
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
 * Service for executing complex queries for {@link Contributor} entities in the database.
 * The main input is a {@link ContributorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ContributorDTO} or a {@link Page} of {@link ContributorDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ContributorQueryService extends QueryService<Contributor> {

    private final Logger log = LoggerFactory.getLogger(ContributorQueryService.class);

    private final ContributorRepository contributorRepository;

    private final ContributorMapper contributorMapper;

    public ContributorQueryService(ContributorRepository contributorRepository, ContributorMapper contributorMapper) {
        this.contributorRepository = contributorRepository;
        this.contributorMapper = contributorMapper;
    }

    /**
     * Return a {@link List} of {@link ContributorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ContributorDTO> findByCriteria(ContributorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Contributor> specification = createSpecification(criteria);
        return contributorMapper.toDto(contributorRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ContributorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ContributorDTO> findByCriteria(ContributorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Contributor> specification = createSpecification(criteria);
        return contributorRepository.findAll(specification, page).map(contributorMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ContributorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Contributor> specification = createSpecification(criteria);
        return contributorRepository.count(specification);
    }

    /**
     * Function to convert {@link ContributorCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Contributor> createSpecification(ContributorCriteria criteria) {
        Specification<Contributor> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Contributor_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Contributor_.name));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), Contributor_.lastName));
            }
            if (criteria.getCpf() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCpf(), Contributor_.cpf));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Contributor_.email));
            }
            if (criteria.getBirthDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBirthDate(), Contributor_.birthDate));
            }
            if (criteria.getAdmissionDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAdmissionDate(), Contributor_.admissionDate));
            }
            if (criteria.getCreationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreationDate(), Contributor_.creationDate));
            }
            if (criteria.getLastUpdateDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastUpdateDate(), Contributor_.lastUpdateDate));
            }
            if (criteria.getSeniorityId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSeniorityId(),
                            root -> root.join(Contributor_.seniority, JoinType.LEFT).get(Seniority_.id)
                        )
                    );
            }
            if (criteria.getCompetencesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCompetencesId(),
                            root -> root.join(Contributor_.competences, JoinType.LEFT).get(Competency_.id)
                        )
                    );
            }
        }
        return specification;
    }
}

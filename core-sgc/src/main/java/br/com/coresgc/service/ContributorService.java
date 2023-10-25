package br.com.coresgc.service;

import br.com.coresgc.service.dto.ContributorDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link br.com.coresgc.domain.Contributor}.
 */
public interface ContributorService {
    /**
     * Save a contributor.
     *
     * @param contributorDTO the entity to save.
     * @return the persisted entity.
     */
    ContributorDTO save(ContributorDTO contributorDTO);

    /**
     * Updates a contributor.
     *
     * @param contributorDTO the entity to update.
     * @return the persisted entity.
     */
    ContributorDTO update(ContributorDTO contributorDTO);

    /**
     * Partially updates a contributor.
     *
     * @param contributorDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ContributorDTO> partialUpdate(ContributorDTO contributorDTO);

    /**
     * Get all the contributors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ContributorDTO> findAll(Pageable pageable);

    /**
     * Get all the contributors with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ContributorDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" contributor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ContributorDTO> findOne(Long id);

    /**
     * Delete the "id" contributor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

package br.com.coresgc.service;

import br.com.coresgc.service.dto.CompetencyDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link br.com.coresgc.domain.Competency}.
 */
public interface CompetencyService {
    /**
     * Save a competency.
     *
     * @param competencyDTO the entity to save.
     * @return the persisted entity.
     */
    CompetencyDTO save(CompetencyDTO competencyDTO);

    /**
     * Updates a competency.
     *
     * @param competencyDTO the entity to update.
     * @return the persisted entity.
     */
    CompetencyDTO update(CompetencyDTO competencyDTO);

    /**
     * Partially updates a competency.
     *
     * @param competencyDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CompetencyDTO> partialUpdate(CompetencyDTO competencyDTO);

    /**
     * Get all the competencies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CompetencyDTO> findAll(Pageable pageable);

    /**
     * Get the "id" competency.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompetencyDTO> findOne(Long id);

    /**
     * Delete the "id" competency.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

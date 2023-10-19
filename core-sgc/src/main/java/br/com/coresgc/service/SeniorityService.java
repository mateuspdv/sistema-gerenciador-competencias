package br.com.coresgc.service;

import br.com.coresgc.service.dto.SeniorityDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link br.com.coresgc.domain.Seniority}.
 */
public interface SeniorityService {
    /**
     * Save a seniority.
     *
     * @param seniorityDTO the entity to save.
     * @return the persisted entity.
     */
    SeniorityDTO save(SeniorityDTO seniorityDTO);

    /**
     * Updates a seniority.
     *
     * @param seniorityDTO the entity to update.
     * @return the persisted entity.
     */
    SeniorityDTO update(SeniorityDTO seniorityDTO);

    /**
     * Partially updates a seniority.
     *
     * @param seniorityDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SeniorityDTO> partialUpdate(SeniorityDTO seniorityDTO);

    /**
     * Get all the seniorities.
     *
     * @return the list of entities.
     */
    List<SeniorityDTO> findAll();

    /**
     * Get the "id" seniority.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SeniorityDTO> findOne(Long id);

    /**
     * Delete the "id" seniority.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

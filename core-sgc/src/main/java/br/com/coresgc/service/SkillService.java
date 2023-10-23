package br.com.coresgc.service;

import br.com.coresgc.service.dto.SkillDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link br.com.coresgc.domain.Skill}.
 */
public interface SkillService {
    /**
     * Save a skill.
     *
     * @param skillDTO the entity to save.
     * @return the persisted entity.
     */
    SkillDTO save(SkillDTO skillDTO);

    /**
     * Updates a skill.
     *
     * @param skillDTO the entity to update.
     * @return the persisted entity.
     */
    SkillDTO update(SkillDTO skillDTO);

    /**
     * Partially updates a skill.
     *
     * @param skillDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SkillDTO> partialUpdate(SkillDTO skillDTO);

    /**
     * Get all the skills.
     *
     * @return the list of entities.
     */
    List<SkillDTO> findAll();

    /**
     * Get the "id" skill.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SkillDTO> findOne(Long id);

    /**
     * Delete the "id" skill.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

}

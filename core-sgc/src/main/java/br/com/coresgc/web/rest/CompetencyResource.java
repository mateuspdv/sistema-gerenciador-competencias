package br.com.coresgc.web.rest;

import br.com.coresgc.repository.CompetencyRepository;
import br.com.coresgc.service.CompetencyQueryService;
import br.com.coresgc.service.CompetencyService;
import br.com.coresgc.service.criteria.CompetencyCriteria;
import br.com.coresgc.service.dto.CompetencyDTO;
import br.com.coresgc.service.dto.ViewCompetencyDTO;
import br.com.coresgc.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link br.com.coresgc.domain.Competency}.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CompetencyResource {

    private final Logger log = LoggerFactory.getLogger(CompetencyResource.class);

    private static final String ENTITY_NAME = "coreSgcCompetency";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompetencyService competencyService;

    private final CompetencyRepository competencyRepository;

    private final CompetencyQueryService competencyQueryService;

    /**
     * {@code POST  /competencies} : Create a new competency.
     *
     * @param competencyDTO the competencyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new competencyDTO, or with status {@code 400 (Bad Request)} if the competency has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/competencies")
    public ResponseEntity<CompetencyDTO> createCompetency(@Valid @RequestBody CompetencyDTO competencyDTO) throws URISyntaxException {
        log.debug("REST request to save Competency : {}", competencyDTO);
        if (competencyDTO.getId() != null) {
            throw new BadRequestAlertException("A new competency cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompetencyDTO result = competencyService.save(competencyDTO);
        return ResponseEntity
            .created(new URI("/api/competencies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /competencies/:id} : Updates an existing competency.
     *
     * @param id            the id of the competencyDTO to save.
     * @param competencyDTO the competencyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competencyDTO,
     * or with status {@code 400 (Bad Request)} if the competencyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the competencyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/competencies/{id}")
    public ResponseEntity<CompetencyDTO> updateCompetency(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CompetencyDTO competencyDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Competency : {}, {}", id, competencyDTO);
        if (competencyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, competencyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!competencyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CompetencyDTO result = competencyService.update(competencyDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, competencyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /competencies/:id} : Partial updates given fields of an existing competency, field will ignore if it is null
     *
     * @param id            the id of the competencyDTO to save.
     * @param competencyDTO the competencyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competencyDTO,
     * or with status {@code 400 (Bad Request)} if the competencyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the competencyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the competencyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/competencies/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<CompetencyDTO> partialUpdateCompetency(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CompetencyDTO competencyDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Competency partially : {}, {}", id, competencyDTO);
        if (competencyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, competencyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!competencyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CompetencyDTO> result = competencyService.partialUpdate(competencyDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, competencyDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /competencies} : get all the competencies.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of competencies in body.
     */
    @GetMapping("/competencies")
    public ResponseEntity<List<CompetencyDTO>> getAllCompetencies(
        CompetencyCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Competencies by criteria: {}", criteria);

        Page<CompetencyDTO> page = competencyQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /competencies/count} : count all the competencies.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/competencies/count")
    public ResponseEntity<Long> countCompetencies(CompetencyCriteria criteria) {
        log.debug("REST request to count Competencies by criteria: {}", criteria);
        return ResponseEntity.ok().body(competencyQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /competencies/:id} : get the "id" competency.
     *
     * @param id the id of the competencyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the competencyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/competencies/{id}")
    public ResponseEntity<CompetencyDTO> getCompetency(@PathVariable Long id) {
        log.debug("REST request to get Competency : {}", id);
        Optional<CompetencyDTO> competencyDTO = competencyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(competencyDTO);
    }

    /**
     * {@code DELETE  /competencies/:id} : delete the "id" competency.
     *
     * @param id the id of the competencyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/competencies/{id}")
    public ResponseEntity<Void> deleteCompetency(@PathVariable Long id) {
        log.debug("REST request to delete Competency : {}", id);
        competencyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * Refactoring
     */

    @GetMapping("/competency")
    public ResponseEntity<List<ViewCompetencyDTO>> searchAllViews() {
        log.debug("REST request to get all competency views");
        return ResponseEntity.ok(competencyService.findAll());
    }

    @PostMapping("/competency")
    public ResponseEntity<CompetencyDTO> saveRefactored(@Valid @RequestBody CompetencyDTO competencyDTO) {
        log.debug("REST request to save Competency : {}", competencyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(competencyService.saveRefactored(competencyDTO));
    }

}

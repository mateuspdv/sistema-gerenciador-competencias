package br.com.coresgc.web.rest;

import br.com.coresgc.repository.ContributorRepository;
import br.com.coresgc.service.ContributorQueryService;
import br.com.coresgc.service.ContributorService;
import br.com.coresgc.service.criteria.ContributorCriteria;
import br.com.coresgc.service.dto.ContributorDTO;
import br.com.coresgc.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.coresgc.domain.Contributor}.
 */
@RestController
@RequestMapping("/api")
public class ContributorResource {

    private final Logger log = LoggerFactory.getLogger(ContributorResource.class);

    private static final String ENTITY_NAME = "coreSgcContributor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContributorService contributorService;

    private final ContributorRepository contributorRepository;

    private final ContributorQueryService contributorQueryService;

    public ContributorResource(
        ContributorService contributorService,
        ContributorRepository contributorRepository,
        ContributorQueryService contributorQueryService
    ) {
        this.contributorService = contributorService;
        this.contributorRepository = contributorRepository;
        this.contributorQueryService = contributorQueryService;
    }

    /**
     * {@code POST  /contributors} : Create a new contributor.
     *
     * @param contributorDTO the contributorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contributorDTO, or with status {@code 400 (Bad Request)} if the contributor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contributors")
    public ResponseEntity<ContributorDTO> createContributor(@Valid @RequestBody ContributorDTO contributorDTO) throws URISyntaxException {
        log.debug("REST request to save Contributor : {}", contributorDTO);
        if (contributorDTO.getId() != null) {
            throw new BadRequestAlertException("A new contributor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContributorDTO result = contributorService.save(contributorDTO);
        return ResponseEntity
            .created(new URI("/api/contributors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contributors/:id} : Updates an existing contributor.
     *
     * @param id the id of the contributorDTO to save.
     * @param contributorDTO the contributorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contributorDTO,
     * or with status {@code 400 (Bad Request)} if the contributorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contributorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contributors/{id}")
    public ResponseEntity<ContributorDTO> updateContributor(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ContributorDTO contributorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Contributor : {}, {}", id, contributorDTO);
        if (contributorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contributorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contributorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ContributorDTO result = contributorService.update(contributorDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contributorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /contributors/:id} : Partial updates given fields of an existing contributor, field will ignore if it is null
     *
     * @param id the id of the contributorDTO to save.
     * @param contributorDTO the contributorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contributorDTO,
     * or with status {@code 400 (Bad Request)} if the contributorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the contributorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the contributorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/contributors/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ContributorDTO> partialUpdateContributor(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ContributorDTO contributorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Contributor partially : {}, {}", id, contributorDTO);
        if (contributorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contributorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contributorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ContributorDTO> result = contributorService.partialUpdate(contributorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contributorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /contributors} : get all the contributors.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contributors in body.
     */
    @GetMapping("/contributors")
    public ResponseEntity<List<ContributorDTO>> getAllContributors(
        ContributorCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Contributors by criteria: {}", criteria);

        Page<ContributorDTO> page = contributorQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contributors/count} : count all the contributors.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/contributors/count")
    public ResponseEntity<Long> countContributors(ContributorCriteria criteria) {
        log.debug("REST request to count Contributors by criteria: {}", criteria);
        return ResponseEntity.ok().body(contributorQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /contributors/:id} : get the "id" contributor.
     *
     * @param id the id of the contributorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contributorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contributors/{id}")
    public ResponseEntity<ContributorDTO> getContributor(@PathVariable Long id) {
        log.debug("REST request to get Contributor : {}", id);
        Optional<ContributorDTO> contributorDTO = contributorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contributorDTO);
    }

    /**
     * {@code DELETE  /contributors/:id} : delete the "id" contributor.
     *
     * @param id the id of the contributorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contributors/{id}")
    public ResponseEntity<Void> deleteContributor(@PathVariable Long id) {
        log.debug("REST request to delete Contributor : {}", id);
        contributorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

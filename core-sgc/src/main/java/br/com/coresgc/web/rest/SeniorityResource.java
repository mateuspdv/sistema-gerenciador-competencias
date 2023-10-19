package br.com.coresgc.web.rest;

import br.com.coresgc.repository.SeniorityRepository;
import br.com.coresgc.service.SeniorityService;
import br.com.coresgc.service.dto.SeniorityDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.web.util.ResponseUtil;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link br.com.coresgc.domain.Seniority}.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SeniorityResource {

    private final Logger log = LoggerFactory.getLogger(SeniorityResource.class);

    private final SeniorityService seniorityService;

    private final SeniorityRepository seniorityRepository;

    /**
     * {@code GET  /seniorities} : get all the seniorities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of seniorities in body.
     */
    @GetMapping("/seniorities")
    public List<SeniorityDTO> getAllSeniorities() {
        log.debug("REST request to get all Seniorities");
        return seniorityService.findAll();
    }

    /**
     * {@code GET  /seniorities/:id} : get the "id" seniority.
     *
     * @param id the id of the seniorityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the seniorityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/seniorities/{id}")
    public ResponseEntity<SeniorityDTO> getSeniority(@PathVariable Long id) {
        log.debug("REST request to get Seniority : {}", id);
        Optional<SeniorityDTO> seniorityDTO = seniorityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(seniorityDTO);
    }

}

package com.ender.tablettop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ender.tablettop.service.LegsService;
import com.ender.tablettop.web.rest.errors.BadRequestAlertException;
import com.ender.tablettop.web.rest.util.HeaderUtil;
import com.ender.tablettop.service.dto.LegsDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Legs.
 */
@RestController
@RequestMapping("/api")
public class LegsResource {

    private final Logger log = LoggerFactory.getLogger(LegsResource.class);

    private static final String ENTITY_NAME = "legs";

    private final LegsService legsService;

    public LegsResource(LegsService legsService) {
        this.legsService = legsService;
    }

    /**
     * POST  /legs : Create a new legs.
     *
     * @param legsDTO the legsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new legsDTO, or with status 400 (Bad Request) if the legs has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/legs")
    @Timed
    public ResponseEntity<LegsDTO> createLegs(@RequestBody LegsDTO legsDTO) throws URISyntaxException {
        log.debug("REST request to save Legs : {}", legsDTO);
        if (legsDTO.getId() != null) {
            throw new BadRequestAlertException("A new legs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LegsDTO result = legsService.save(legsDTO);
        return ResponseEntity.created(new URI("/api/legs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /legs : Updates an existing legs.
     *
     * @param legsDTO the legsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated legsDTO,
     * or with status 400 (Bad Request) if the legsDTO is not valid,
     * or with status 500 (Internal Server Error) if the legsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/legs")
    @Timed
    public ResponseEntity<LegsDTO> updateLegs(@RequestBody LegsDTO legsDTO) throws URISyntaxException {
        log.debug("REST request to update Legs : {}", legsDTO);
        if (legsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LegsDTO result = legsService.save(legsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, legsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /legs : get all the legs.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of legs in body
     */
    @GetMapping("/legs")
    @Timed
    public List<LegsDTO> getAllLegs(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Legs");
        return legsService.findAll();
    }

    /**
     * GET  /legs/:id : get the "id" legs.
     *
     * @param id the id of the legsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the legsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/legs/{id}")
    @Timed
    public ResponseEntity<LegsDTO> getLegs(@PathVariable Long id) {
        log.debug("REST request to get Legs : {}", id);
        Optional<LegsDTO> legsDTO = legsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(legsDTO);
    }

    /**
     * DELETE  /legs/:id : delete the "id" legs.
     *
     * @param id the id of the legsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/legs/{id}")
    @Timed
    public ResponseEntity<Void> deleteLegs(@PathVariable Long id) {
        log.debug("REST request to delete Legs : {}", id);
        legsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

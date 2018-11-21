package com.ender.tablettop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ender.tablettop.service.LeftHandService;
import com.ender.tablettop.web.rest.errors.BadRequestAlertException;
import com.ender.tablettop.web.rest.util.HeaderUtil;
import com.ender.tablettop.service.dto.LeftHandDTO;
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
 * REST controller for managing LeftHand.
 */
@RestController
@RequestMapping("/api")
public class LeftHandResource {

    private final Logger log = LoggerFactory.getLogger(LeftHandResource.class);

    private static final String ENTITY_NAME = "leftHand";

    private final LeftHandService leftHandService;

    public LeftHandResource(LeftHandService leftHandService) {
        this.leftHandService = leftHandService;
    }

    /**
     * POST  /left-hands : Create a new leftHand.
     *
     * @param leftHandDTO the leftHandDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new leftHandDTO, or with status 400 (Bad Request) if the leftHand has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/left-hands")
    @Timed
    public ResponseEntity<LeftHandDTO> createLeftHand(@RequestBody LeftHandDTO leftHandDTO) throws URISyntaxException {
        log.debug("REST request to save LeftHand : {}", leftHandDTO);
        if (leftHandDTO.getId() != null) {
            throw new BadRequestAlertException("A new leftHand cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeftHandDTO result = leftHandService.save(leftHandDTO);
        return ResponseEntity.created(new URI("/api/left-hands/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /left-hands : Updates an existing leftHand.
     *
     * @param leftHandDTO the leftHandDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated leftHandDTO,
     * or with status 400 (Bad Request) if the leftHandDTO is not valid,
     * or with status 500 (Internal Server Error) if the leftHandDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/left-hands")
    @Timed
    public ResponseEntity<LeftHandDTO> updateLeftHand(@RequestBody LeftHandDTO leftHandDTO) throws URISyntaxException {
        log.debug("REST request to update LeftHand : {}", leftHandDTO);
        if (leftHandDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LeftHandDTO result = leftHandService.save(leftHandDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, leftHandDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /left-hands : get all the leftHands.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of leftHands in body
     */
    @GetMapping("/left-hands")
    @Timed
    public List<LeftHandDTO> getAllLeftHands(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all LeftHands");
        return leftHandService.findAll();
    }

    /**
     * GET  /left-hands/:id : get the "id" leftHand.
     *
     * @param id the id of the leftHandDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the leftHandDTO, or with status 404 (Not Found)
     */
    @GetMapping("/left-hands/{id}")
    @Timed
    public ResponseEntity<LeftHandDTO> getLeftHand(@PathVariable Long id) {
        log.debug("REST request to get LeftHand : {}", id);
        Optional<LeftHandDTO> leftHandDTO = leftHandService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leftHandDTO);
    }

    /**
     * DELETE  /left-hands/:id : delete the "id" leftHand.
     *
     * @param id the id of the leftHandDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/left-hands/{id}")
    @Timed
    public ResponseEntity<Void> deleteLeftHand(@PathVariable Long id) {
        log.debug("REST request to delete LeftHand : {}", id);
        leftHandService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

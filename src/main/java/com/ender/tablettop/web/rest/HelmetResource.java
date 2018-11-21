package com.ender.tablettop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ender.tablettop.service.HelmetService;
import com.ender.tablettop.web.rest.errors.BadRequestAlertException;
import com.ender.tablettop.web.rest.util.HeaderUtil;
import com.ender.tablettop.service.dto.HelmetDTO;
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
 * REST controller for managing Helmet.
 */
@RestController
@RequestMapping("/api")
public class HelmetResource {

    private final Logger log = LoggerFactory.getLogger(HelmetResource.class);

    private static final String ENTITY_NAME = "helmet";

    private final HelmetService helmetService;

    public HelmetResource(HelmetService helmetService) {
        this.helmetService = helmetService;
    }

    /**
     * POST  /helmets : Create a new helmet.
     *
     * @param helmetDTO the helmetDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new helmetDTO, or with status 400 (Bad Request) if the helmet has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/helmets")
    @Timed
    public ResponseEntity<HelmetDTO> createHelmet(@RequestBody HelmetDTO helmetDTO) throws URISyntaxException {
        log.debug("REST request to save Helmet : {}", helmetDTO);
        if (helmetDTO.getId() != null) {
            throw new BadRequestAlertException("A new helmet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HelmetDTO result = helmetService.save(helmetDTO);
        return ResponseEntity.created(new URI("/api/helmets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /helmets : Updates an existing helmet.
     *
     * @param helmetDTO the helmetDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated helmetDTO,
     * or with status 400 (Bad Request) if the helmetDTO is not valid,
     * or with status 500 (Internal Server Error) if the helmetDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/helmets")
    @Timed
    public ResponseEntity<HelmetDTO> updateHelmet(@RequestBody HelmetDTO helmetDTO) throws URISyntaxException {
        log.debug("REST request to update Helmet : {}", helmetDTO);
        if (helmetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HelmetDTO result = helmetService.save(helmetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, helmetDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /helmets : get all the helmets.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of helmets in body
     */
    @GetMapping("/helmets")
    @Timed
    public List<HelmetDTO> getAllHelmets(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Helmets");
        return helmetService.findAll();
    }

    /**
     * GET  /helmets/:id : get the "id" helmet.
     *
     * @param id the id of the helmetDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the helmetDTO, or with status 404 (Not Found)
     */
    @GetMapping("/helmets/{id}")
    @Timed
    public ResponseEntity<HelmetDTO> getHelmet(@PathVariable Long id) {
        log.debug("REST request to get Helmet : {}", id);
        Optional<HelmetDTO> helmetDTO = helmetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(helmetDTO);
    }

    /**
     * DELETE  /helmets/:id : delete the "id" helmet.
     *
     * @param id the id of the helmetDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/helmets/{id}")
    @Timed
    public ResponseEntity<Void> deleteHelmet(@PathVariable Long id) {
        log.debug("REST request to delete Helmet : {}", id);
        helmetService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

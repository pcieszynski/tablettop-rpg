package com.ender.tablettop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ender.tablettop.service.GlovesService;
import com.ender.tablettop.web.rest.errors.BadRequestAlertException;
import com.ender.tablettop.web.rest.util.HeaderUtil;
import com.ender.tablettop.service.dto.GlovesDTO;
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
 * REST controller for managing Gloves.
 */
@RestController
@RequestMapping("/api")
public class GlovesResource {

    private final Logger log = LoggerFactory.getLogger(GlovesResource.class);

    private static final String ENTITY_NAME = "gloves";

    private final GlovesService glovesService;

    public GlovesResource(GlovesService glovesService) {
        this.glovesService = glovesService;
    }

    /**
     * POST  /gloves : Create a new gloves.
     *
     * @param glovesDTO the glovesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new glovesDTO, or with status 400 (Bad Request) if the gloves has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/gloves")
    @Timed
    public ResponseEntity<GlovesDTO> createGloves(@RequestBody GlovesDTO glovesDTO) throws URISyntaxException {
        log.debug("REST request to save Gloves : {}", glovesDTO);
        if (glovesDTO.getId() != null) {
            throw new BadRequestAlertException("A new gloves cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GlovesDTO result = glovesService.save(glovesDTO);
        return ResponseEntity.created(new URI("/api/gloves/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /gloves : Updates an existing gloves.
     *
     * @param glovesDTO the glovesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated glovesDTO,
     * or with status 400 (Bad Request) if the glovesDTO is not valid,
     * or with status 500 (Internal Server Error) if the glovesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/gloves")
    @Timed
    public ResponseEntity<GlovesDTO> updateGloves(@RequestBody GlovesDTO glovesDTO) throws URISyntaxException {
        log.debug("REST request to update Gloves : {}", glovesDTO);
        if (glovesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GlovesDTO result = glovesService.save(glovesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, glovesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /gloves : get all the gloves.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of gloves in body
     */
    @GetMapping("/gloves")
    @Timed
    public List<GlovesDTO> getAllGloves(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Gloves");
        return glovesService.findAll();
    }

    /**
     * GET  /gloves/:id : get the "id" gloves.
     *
     * @param id the id of the glovesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the glovesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/gloves/{id}")
    @Timed
    public ResponseEntity<GlovesDTO> getGloves(@PathVariable Long id) {
        log.debug("REST request to get Gloves : {}", id);
        Optional<GlovesDTO> glovesDTO = glovesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(glovesDTO);
    }

    /**
     * DELETE  /gloves/:id : delete the "id" gloves.
     *
     * @param id the id of the glovesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/gloves/{id}")
    @Timed
    public ResponseEntity<Void> deleteGloves(@PathVariable Long id) {
        log.debug("REST request to delete Gloves : {}", id);
        glovesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

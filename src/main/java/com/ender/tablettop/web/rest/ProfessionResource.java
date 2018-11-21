package com.ender.tablettop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ender.tablettop.service.ProfessionService;
import com.ender.tablettop.web.rest.errors.BadRequestAlertException;
import com.ender.tablettop.web.rest.util.HeaderUtil;
import com.ender.tablettop.service.dto.ProfessionDTO;
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
 * REST controller for managing Profession.
 */
@RestController
@RequestMapping("/api")
public class ProfessionResource {

    private final Logger log = LoggerFactory.getLogger(ProfessionResource.class);

    private static final String ENTITY_NAME = "profession";

    private final ProfessionService professionService;

    public ProfessionResource(ProfessionService professionService) {
        this.professionService = professionService;
    }

    /**
     * POST  /professions : Create a new profession.
     *
     * @param professionDTO the professionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new professionDTO, or with status 400 (Bad Request) if the profession has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/professions")
    @Timed
    public ResponseEntity<ProfessionDTO> createProfession(@RequestBody ProfessionDTO professionDTO) throws URISyntaxException {
        log.debug("REST request to save Profession : {}", professionDTO);
        if (professionDTO.getId() != null) {
            throw new BadRequestAlertException("A new profession cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProfessionDTO result = professionService.save(professionDTO);
        return ResponseEntity.created(new URI("/api/professions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /professions : Updates an existing profession.
     *
     * @param professionDTO the professionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated professionDTO,
     * or with status 400 (Bad Request) if the professionDTO is not valid,
     * or with status 500 (Internal Server Error) if the professionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/professions")
    @Timed
    public ResponseEntity<ProfessionDTO> updateProfession(@RequestBody ProfessionDTO professionDTO) throws URISyntaxException {
        log.debug("REST request to update Profession : {}", professionDTO);
        if (professionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProfessionDTO result = professionService.save(professionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, professionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /professions : get all the professions.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of professions in body
     */
    @GetMapping("/professions")
    @Timed
    public List<ProfessionDTO> getAllProfessions(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Professions");
        return professionService.findAll();
    }

    /**
     * GET  /professions/:id : get the "id" profession.
     *
     * @param id the id of the professionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the professionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/professions/{id}")
    @Timed
    public ResponseEntity<ProfessionDTO> getProfession(@PathVariable Long id) {
        log.debug("REST request to get Profession : {}", id);
        Optional<ProfessionDTO> professionDTO = professionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(professionDTO);
    }

    /**
     * DELETE  /professions/:id : delete the "id" profession.
     *
     * @param id the id of the professionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/professions/{id}")
    @Timed
    public ResponseEntity<Void> deleteProfession(@PathVariable Long id) {
        log.debug("REST request to delete Profession : {}", id);
        professionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

package com.ender.tablettop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ender.tablettop.service.MonsterTypeService;
import com.ender.tablettop.web.rest.errors.BadRequestAlertException;
import com.ender.tablettop.web.rest.util.HeaderUtil;
import com.ender.tablettop.service.dto.MonsterTypeDTO;
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
 * REST controller for managing MonsterType.
 */
@RestController
@RequestMapping("/api")
public class MonsterTypeResource {

    private final Logger log = LoggerFactory.getLogger(MonsterTypeResource.class);

    private static final String ENTITY_NAME = "monsterType";

    private final MonsterTypeService monsterTypeService;

    public MonsterTypeResource(MonsterTypeService monsterTypeService) {
        this.monsterTypeService = monsterTypeService;
    }

    /**
     * POST  /monster-types : Create a new monsterType.
     *
     * @param monsterTypeDTO the monsterTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new monsterTypeDTO, or with status 400 (Bad Request) if the monsterType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/monster-types")
    @Timed
    public ResponseEntity<MonsterTypeDTO> createMonsterType(@RequestBody MonsterTypeDTO monsterTypeDTO) throws URISyntaxException {
        log.debug("REST request to save MonsterType : {}", monsterTypeDTO);
        if (monsterTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new monsterType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MonsterTypeDTO result = monsterTypeService.save(monsterTypeDTO);
        return ResponseEntity.created(new URI("/api/monster-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /monster-types : Updates an existing monsterType.
     *
     * @param monsterTypeDTO the monsterTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated monsterTypeDTO,
     * or with status 400 (Bad Request) if the monsterTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the monsterTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/monster-types")
    @Timed
    public ResponseEntity<MonsterTypeDTO> updateMonsterType(@RequestBody MonsterTypeDTO monsterTypeDTO) throws URISyntaxException {
        log.debug("REST request to update MonsterType : {}", monsterTypeDTO);
        if (monsterTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MonsterTypeDTO result = monsterTypeService.save(monsterTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, monsterTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /monster-types : get all the monsterTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of monsterTypes in body
     */
    @GetMapping("/monster-types")
    @Timed
    public List<MonsterTypeDTO> getAllMonsterTypes() {
        log.debug("REST request to get all MonsterTypes");
        return monsterTypeService.findAll();
    }

    /**
     * GET  /monster-types/:id : get the "id" monsterType.
     *
     * @param id the id of the monsterTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the monsterTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/monster-types/{id}")
    @Timed
    public ResponseEntity<MonsterTypeDTO> getMonsterType(@PathVariable Long id) {
        log.debug("REST request to get MonsterType : {}", id);
        Optional<MonsterTypeDTO> monsterTypeDTO = monsterTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(monsterTypeDTO);
    }

    /**
     * DELETE  /monster-types/:id : delete the "id" monsterType.
     *
     * @param id the id of the monsterTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/monster-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteMonsterType(@PathVariable Long id) {
        log.debug("REST request to delete MonsterType : {}", id);
        monsterTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

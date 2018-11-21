package com.ender.tablettop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ender.tablettop.service.MonsterService;
import com.ender.tablettop.web.rest.errors.BadRequestAlertException;
import com.ender.tablettop.web.rest.util.HeaderUtil;
import com.ender.tablettop.service.dto.MonsterDTO;
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
 * REST controller for managing Monster.
 */
@RestController
@RequestMapping("/api")
public class MonsterResource {

    private final Logger log = LoggerFactory.getLogger(MonsterResource.class);

    private static final String ENTITY_NAME = "monster";

    private final MonsterService monsterService;

    public MonsterResource(MonsterService monsterService) {
        this.monsterService = monsterService;
    }

    /**
     * POST  /monsters : Create a new monster.
     *
     * @param monsterDTO the monsterDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new monsterDTO, or with status 400 (Bad Request) if the monster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/monsters")
    @Timed
    public ResponseEntity<MonsterDTO> createMonster(@RequestBody MonsterDTO monsterDTO) throws URISyntaxException {
        log.debug("REST request to save Monster : {}", monsterDTO);
        if (monsterDTO.getId() != null) {
            throw new BadRequestAlertException("A new monster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MonsterDTO result = monsterService.save(monsterDTO);
        return ResponseEntity.created(new URI("/api/monsters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /monsters : Updates an existing monster.
     *
     * @param monsterDTO the monsterDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated monsterDTO,
     * or with status 400 (Bad Request) if the monsterDTO is not valid,
     * or with status 500 (Internal Server Error) if the monsterDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/monsters")
    @Timed
    public ResponseEntity<MonsterDTO> updateMonster(@RequestBody MonsterDTO monsterDTO) throws URISyntaxException {
        log.debug("REST request to update Monster : {}", monsterDTO);
        if (monsterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MonsterDTO result = monsterService.save(monsterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, monsterDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /monsters : get all the monsters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of monsters in body
     */
    @GetMapping("/monsters")
    @Timed
    public List<MonsterDTO> getAllMonsters() {
        log.debug("REST request to get all Monsters");
        return monsterService.findAll();
    }

    /**
     * GET  /monsters/:id : get the "id" monster.
     *
     * @param id the id of the monsterDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the monsterDTO, or with status 404 (Not Found)
     */
    @GetMapping("/monsters/{id}")
    @Timed
    public ResponseEntity<MonsterDTO> getMonster(@PathVariable Long id) {
        log.debug("REST request to get Monster : {}", id);
        Optional<MonsterDTO> monsterDTO = monsterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(monsterDTO);
    }

    /**
     * DELETE  /monsters/:id : delete the "id" monster.
     *
     * @param id the id of the monsterDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/monsters/{id}")
    @Timed
    public ResponseEntity<Void> deleteMonster(@PathVariable Long id) {
        log.debug("REST request to delete Monster : {}", id);
        monsterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

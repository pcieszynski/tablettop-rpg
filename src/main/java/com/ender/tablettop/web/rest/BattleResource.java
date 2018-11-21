package com.ender.tablettop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ender.tablettop.service.BattleService;
import com.ender.tablettop.web.rest.errors.BadRequestAlertException;
import com.ender.tablettop.web.rest.util.HeaderUtil;
import com.ender.tablettop.service.dto.BattleDTO;
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
 * REST controller for managing Battle.
 */
@RestController
@RequestMapping("/api")
public class BattleResource {

    private final Logger log = LoggerFactory.getLogger(BattleResource.class);

    private static final String ENTITY_NAME = "battle";

    private final BattleService battleService;

    public BattleResource(BattleService battleService) {
        this.battleService = battleService;
    }

    /**
     * POST  /battles : Create a new battle.
     *
     * @param battleDTO the battleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new battleDTO, or with status 400 (Bad Request) if the battle has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/battles")
    @Timed
    public ResponseEntity<BattleDTO> createBattle(@RequestBody BattleDTO battleDTO) throws URISyntaxException {
        log.debug("REST request to save Battle : {}", battleDTO);
        if (battleDTO.getId() != null) {
            throw new BadRequestAlertException("A new battle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BattleDTO result = battleService.save(battleDTO);
        return ResponseEntity.created(new URI("/api/battles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /battles : Updates an existing battle.
     *
     * @param battleDTO the battleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated battleDTO,
     * or with status 400 (Bad Request) if the battleDTO is not valid,
     * or with status 500 (Internal Server Error) if the battleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/battles")
    @Timed
    public ResponseEntity<BattleDTO> updateBattle(@RequestBody BattleDTO battleDTO) throws URISyntaxException {
        log.debug("REST request to update Battle : {}", battleDTO);
        if (battleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BattleDTO result = battleService.save(battleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, battleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /battles : get all the battles.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of battles in body
     */
    @GetMapping("/battles")
    @Timed
    public List<BattleDTO> getAllBattles(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Battles");
        return battleService.findAll();
    }

    /**
     * GET  /battles/:id : get the "id" battle.
     *
     * @param id the id of the battleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the battleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/battles/{id}")
    @Timed
    public ResponseEntity<BattleDTO> getBattle(@PathVariable Long id) {
        log.debug("REST request to get Battle : {}", id);
        Optional<BattleDTO> battleDTO = battleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(battleDTO);
    }

    /**
     * DELETE  /battles/:id : delete the "id" battle.
     *
     * @param id the id of the battleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/battles/{id}")
    @Timed
    public ResponseEntity<Void> deleteBattle(@PathVariable Long id) {
        log.debug("REST request to delete Battle : {}", id);
        battleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

package com.ender.tablettop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ender.tablettop.service.GamemasterService;
import com.ender.tablettop.web.rest.errors.BadRequestAlertException;
import com.ender.tablettop.web.rest.util.HeaderUtil;
import com.ender.tablettop.service.dto.GamemasterDTO;
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
 * REST controller for managing Gamemaster.
 */
@RestController
@RequestMapping("/api")
public class GamemasterResource {

    private final Logger log = LoggerFactory.getLogger(GamemasterResource.class);

    private static final String ENTITY_NAME = "gamemaster";

    private final GamemasterService gamemasterService;

    public GamemasterResource(GamemasterService gamemasterService) {
        this.gamemasterService = gamemasterService;
    }

    /**
     * POST  /gamemasters : Create a new gamemaster.
     *
     * @param gamemasterDTO the gamemasterDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gamemasterDTO, or with status 400 (Bad Request) if the gamemaster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/gamemasters")
    @Timed
    public ResponseEntity<GamemasterDTO> createGamemaster(@RequestBody GamemasterDTO gamemasterDTO) throws URISyntaxException {
        log.debug("REST request to save Gamemaster : {}", gamemasterDTO);
        if (gamemasterDTO.getId() != null) {
            throw new BadRequestAlertException("A new gamemaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GamemasterDTO result = gamemasterService.save(gamemasterDTO);
        return ResponseEntity.created(new URI("/api/gamemasters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /gamemasters : Updates an existing gamemaster.
     *
     * @param gamemasterDTO the gamemasterDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gamemasterDTO,
     * or with status 400 (Bad Request) if the gamemasterDTO is not valid,
     * or with status 500 (Internal Server Error) if the gamemasterDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/gamemasters")
    @Timed
    public ResponseEntity<GamemasterDTO> updateGamemaster(@RequestBody GamemasterDTO gamemasterDTO) throws URISyntaxException {
        log.debug("REST request to update Gamemaster : {}", gamemasterDTO);
        if (gamemasterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GamemasterDTO result = gamemasterService.save(gamemasterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, gamemasterDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /gamemasters : get all the gamemasters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of gamemasters in body
     */
    @GetMapping("/gamemasters")
    @Timed
    public List<GamemasterDTO> getAllGamemasters() {
        log.debug("REST request to get all Gamemasters");
        return gamemasterService.findAll();
    }

    /**
     * GET  /gamemasters/:id : get the "id" gamemaster.
     *
     * @param id the id of the gamemasterDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gamemasterDTO, or with status 404 (Not Found)
     */
    @GetMapping("/gamemasters/{id}")
    @Timed
    public ResponseEntity<GamemasterDTO> getGamemaster(@PathVariable Long id) {
        log.debug("REST request to get Gamemaster : {}", id);
        Optional<GamemasterDTO> gamemasterDTO = gamemasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gamemasterDTO);
    }

    /**
     * DELETE  /gamemasters/:id : delete the "id" gamemaster.
     *
     * @param id the id of the gamemasterDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/gamemasters/{id}")
    @Timed
    public ResponseEntity<Void> deleteGamemaster(@PathVariable Long id) {
        log.debug("REST request to delete Gamemaster : {}", id);
        gamemasterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

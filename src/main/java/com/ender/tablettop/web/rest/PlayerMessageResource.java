package com.ender.tablettop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ender.tablettop.service.PlayerMessageService;
import com.ender.tablettop.web.rest.errors.BadRequestAlertException;
import com.ender.tablettop.web.rest.util.HeaderUtil;
import com.ender.tablettop.service.dto.PlayerMessageDTO;
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
 * REST controller for managing PlayerMessage.
 */
@RestController
@RequestMapping("/api")
public class PlayerMessageResource {

    private final Logger log = LoggerFactory.getLogger(PlayerMessageResource.class);

    private static final String ENTITY_NAME = "playerMessage";

    private final PlayerMessageService playerMessageService;

    public PlayerMessageResource(PlayerMessageService playerMessageService) {
        this.playerMessageService = playerMessageService;
    }

    /**
     * POST  /player-messages : Create a new playerMessage.
     *
     * @param playerMessageDTO the playerMessageDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new playerMessageDTO, or with status 400 (Bad Request) if the playerMessage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/player-messages")
    @Timed
    public ResponseEntity<PlayerMessageDTO> createPlayerMessage(@RequestBody PlayerMessageDTO playerMessageDTO) throws URISyntaxException {
        log.debug("REST request to save PlayerMessage : {}", playerMessageDTO);
        if (playerMessageDTO.getId() != null) {
            throw new BadRequestAlertException("A new playerMessage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlayerMessageDTO result = playerMessageService.save(playerMessageDTO);
        return ResponseEntity.created(new URI("/api/player-messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /player-messages : Updates an existing playerMessage.
     *
     * @param playerMessageDTO the playerMessageDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated playerMessageDTO,
     * or with status 400 (Bad Request) if the playerMessageDTO is not valid,
     * or with status 500 (Internal Server Error) if the playerMessageDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/player-messages")
    @Timed
    public ResponseEntity<PlayerMessageDTO> updatePlayerMessage(@RequestBody PlayerMessageDTO playerMessageDTO) throws URISyntaxException {
        log.debug("REST request to update PlayerMessage : {}", playerMessageDTO);
        if (playerMessageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlayerMessageDTO result = playerMessageService.save(playerMessageDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, playerMessageDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /player-messages : get all the playerMessages.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of playerMessages in body
     */
    @GetMapping("/player-messages")
    @Timed
    public List<PlayerMessageDTO> getAllPlayerMessages() {
        log.debug("REST request to get all PlayerMessages");
        return playerMessageService.findAll();
    }

    /**
     * GET  /player-messages/:id : get the "id" playerMessage.
     *
     * @param id the id of the playerMessageDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the playerMessageDTO, or with status 404 (Not Found)
     */
    @GetMapping("/player-messages/{id}")
    @Timed
    public ResponseEntity<PlayerMessageDTO> getPlayerMessage(@PathVariable Long id) {
        log.debug("REST request to get PlayerMessage : {}", id);
        Optional<PlayerMessageDTO> playerMessageDTO = playerMessageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(playerMessageDTO);
    }

    /**
     * DELETE  /player-messages/:id : delete the "id" playerMessage.
     *
     * @param id the id of the playerMessageDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/player-messages/{id}")
    @Timed
    public ResponseEntity<Void> deletePlayerMessage(@PathVariable Long id) {
        log.debug("REST request to delete PlayerMessage : {}", id);
        playerMessageService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

package com.ender.tablettop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ender.tablettop.service.ArmourService;
import com.ender.tablettop.web.rest.errors.BadRequestAlertException;
import com.ender.tablettop.web.rest.util.HeaderUtil;
import com.ender.tablettop.service.dto.ArmourDTO;
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
 * REST controller for managing Armour.
 */
@RestController
@RequestMapping("/api")
public class ArmourResource {

    private final Logger log = LoggerFactory.getLogger(ArmourResource.class);

    private static final String ENTITY_NAME = "armour";

    private final ArmourService armourService;

    public ArmourResource(ArmourService armourService) {
        this.armourService = armourService;
    }

    /**
     * POST  /armours : Create a new armour.
     *
     * @param armourDTO the armourDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new armourDTO, or with status 400 (Bad Request) if the armour has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/armours")
    @Timed
    public ResponseEntity<ArmourDTO> createArmour(@RequestBody ArmourDTO armourDTO) throws URISyntaxException {
        log.debug("REST request to save Armour : {}", armourDTO);
        if (armourDTO.getId() != null) {
            throw new BadRequestAlertException("A new armour cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArmourDTO result = armourService.save(armourDTO);
        return ResponseEntity.created(new URI("/api/armours/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /armours : Updates an existing armour.
     *
     * @param armourDTO the armourDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated armourDTO,
     * or with status 400 (Bad Request) if the armourDTO is not valid,
     * or with status 500 (Internal Server Error) if the armourDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/armours")
    @Timed
    public ResponseEntity<ArmourDTO> updateArmour(@RequestBody ArmourDTO armourDTO) throws URISyntaxException {
        log.debug("REST request to update Armour : {}", armourDTO);
        if (armourDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ArmourDTO result = armourService.save(armourDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, armourDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /armours : get all the armours.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of armours in body
     */
    @GetMapping("/armours")
    @Timed
    public List<ArmourDTO> getAllArmours(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Armours");
        return armourService.findAll();
    }

    /**
     * GET  /armours/:id : get the "id" armour.
     *
     * @param id the id of the armourDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the armourDTO, or with status 404 (Not Found)
     */
    @GetMapping("/armours/{id}")
    @Timed
    public ResponseEntity<ArmourDTO> getArmour(@PathVariable Long id) {
        log.debug("REST request to get Armour : {}", id);
        Optional<ArmourDTO> armourDTO = armourService.findOne(id);
        return ResponseUtil.wrapOrNotFound(armourDTO);
    }

    /**
     * DELETE  /armours/:id : delete the "id" armour.
     *
     * @param id the id of the armourDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/armours/{id}")
    @Timed
    public ResponseEntity<Void> deleteArmour(@PathVariable Long id) {
        log.debug("REST request to delete Armour : {}", id);
        armourService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

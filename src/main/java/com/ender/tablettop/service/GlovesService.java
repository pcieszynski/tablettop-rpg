package com.ender.tablettop.service;

import com.ender.tablettop.service.dto.GlovesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Gloves.
 */
public interface GlovesService {

    /**
     * Save a gloves.
     *
     * @param glovesDTO the entity to save
     * @return the persisted entity
     */
    GlovesDTO save(GlovesDTO glovesDTO);

    /**
     * Get all the gloves.
     *
     * @return the list of entities
     */
    List<GlovesDTO> findAll();

    /**
     * Get all the Gloves with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<GlovesDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" gloves.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<GlovesDTO> findOne(Long id);

    /**
     * Delete the "id" gloves.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

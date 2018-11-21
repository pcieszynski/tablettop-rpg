package com.ender.tablettop.service;

import com.ender.tablettop.service.dto.ArmourDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Armour.
 */
public interface ArmourService {

    /**
     * Save a armour.
     *
     * @param armourDTO the entity to save
     * @return the persisted entity
     */
    ArmourDTO save(ArmourDTO armourDTO);

    /**
     * Get all the armours.
     *
     * @return the list of entities
     */
    List<ArmourDTO> findAll();

    /**
     * Get all the Armour with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<ArmourDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" armour.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ArmourDTO> findOne(Long id);

    /**
     * Delete the "id" armour.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

package com.ender.tablettop.service;

import com.ender.tablettop.service.dto.LeftHandDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing LeftHand.
 */
public interface LeftHandService {

    /**
     * Save a leftHand.
     *
     * @param leftHandDTO the entity to save
     * @return the persisted entity
     */
    LeftHandDTO save(LeftHandDTO leftHandDTO);

    /**
     * Get all the leftHands.
     *
     * @return the list of entities
     */
    List<LeftHandDTO> findAll();

    /**
     * Get all the LeftHand with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<LeftHandDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" leftHand.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<LeftHandDTO> findOne(Long id);

    /**
     * Delete the "id" leftHand.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

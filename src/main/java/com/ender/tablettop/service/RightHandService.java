package com.ender.tablettop.service;

import com.ender.tablettop.service.dto.RightHandDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing RightHand.
 */
public interface RightHandService {

    /**
     * Save a rightHand.
     *
     * @param rightHandDTO the entity to save
     * @return the persisted entity
     */
    RightHandDTO save(RightHandDTO rightHandDTO);

    /**
     * Get all the rightHands.
     *
     * @return the list of entities
     */
    List<RightHandDTO> findAll();

    /**
     * Get all the RightHand with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<RightHandDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" rightHand.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RightHandDTO> findOne(Long id);

    /**
     * Delete the "id" rightHand.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

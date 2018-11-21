package com.ender.tablettop.service;

import com.ender.tablettop.service.dto.LegsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Legs.
 */
public interface LegsService {

    /**
     * Save a legs.
     *
     * @param legsDTO the entity to save
     * @return the persisted entity
     */
    LegsDTO save(LegsDTO legsDTO);

    /**
     * Get all the legs.
     *
     * @return the list of entities
     */
    List<LegsDTO> findAll();

    /**
     * Get all the Legs with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<LegsDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" legs.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<LegsDTO> findOne(Long id);

    /**
     * Delete the "id" legs.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

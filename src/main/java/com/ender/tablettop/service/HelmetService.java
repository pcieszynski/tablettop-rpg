package com.ender.tablettop.service;

import com.ender.tablettop.service.dto.HelmetDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Helmet.
 */
public interface HelmetService {

    /**
     * Save a helmet.
     *
     * @param helmetDTO the entity to save
     * @return the persisted entity
     */
    HelmetDTO save(HelmetDTO helmetDTO);

    /**
     * Get all the helmets.
     *
     * @return the list of entities
     */
    List<HelmetDTO> findAll();

    /**
     * Get all the Helmet with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<HelmetDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" helmet.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<HelmetDTO> findOne(Long id);

    /**
     * Delete the "id" helmet.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

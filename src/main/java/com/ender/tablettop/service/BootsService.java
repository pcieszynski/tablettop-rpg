package com.ender.tablettop.service;

import com.ender.tablettop.service.dto.BootsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Boots.
 */
public interface BootsService {

    /**
     * Save a boots.
     *
     * @param bootsDTO the entity to save
     * @return the persisted entity
     */
    BootsDTO save(BootsDTO bootsDTO);

    /**
     * Get all the boots.
     *
     * @return the list of entities
     */
    List<BootsDTO> findAll();

    /**
     * Get all the Boots with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<BootsDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" boots.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<BootsDTO> findOne(Long id);

    /**
     * Delete the "id" boots.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

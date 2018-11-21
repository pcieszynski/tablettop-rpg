package com.ender.tablettop.service;

import com.ender.tablettop.service.dto.ProfessionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Profession.
 */
public interface ProfessionService {

    /**
     * Save a profession.
     *
     * @param professionDTO the entity to save
     * @return the persisted entity
     */
    ProfessionDTO save(ProfessionDTO professionDTO);

    /**
     * Get all the professions.
     *
     * @return the list of entities
     */
    List<ProfessionDTO> findAll();

    /**
     * Get all the Profession with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<ProfessionDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" profession.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ProfessionDTO> findOne(Long id);

    /**
     * Delete the "id" profession.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

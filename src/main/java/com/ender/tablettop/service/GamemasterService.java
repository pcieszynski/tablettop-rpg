package com.ender.tablettop.service;

import com.ender.tablettop.service.dto.GamemasterDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Gamemaster.
 */
public interface GamemasterService {

    /**
     * Save a gamemaster.
     *
     * @param gamemasterDTO the entity to save
     * @return the persisted entity
     */
    GamemasterDTO save(GamemasterDTO gamemasterDTO);

    /**
     * Get all the gamemasters.
     *
     * @return the list of entities
     */
    List<GamemasterDTO> findAll();


    /**
     * Get the "id" gamemaster.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<GamemasterDTO> findOne(Long id);

    /**
     * Delete the "id" gamemaster.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

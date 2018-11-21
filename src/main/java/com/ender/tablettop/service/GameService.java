package com.ender.tablettop.service;

import com.ender.tablettop.service.dto.GameDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Game.
 */
public interface GameService {

    /**
     * Save a game.
     *
     * @param gameDTO the entity to save
     * @return the persisted entity
     */
    GameDTO save(GameDTO gameDTO);

    /**
     * Get all the games.
     *
     * @return the list of entities
     */
    List<GameDTO> findAll();

    /**
     * Get all the Game with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<GameDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" game.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<GameDTO> findOne(Long id);

    /**
     * Delete the "id" game.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

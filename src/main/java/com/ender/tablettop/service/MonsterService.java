package com.ender.tablettop.service;

import com.ender.tablettop.service.dto.MonsterDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Monster.
 */
public interface MonsterService {

    /**
     * Save a monster.
     *
     * @param monsterDTO the entity to save
     * @return the persisted entity
     */
    MonsterDTO save(MonsterDTO monsterDTO);

    /**
     * Get all the monsters.
     *
     * @return the list of entities
     */
    List<MonsterDTO> findAll();


    /**
     * Get the "id" monster.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MonsterDTO> findOne(Long id);

    /**
     * Delete the "id" monster.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

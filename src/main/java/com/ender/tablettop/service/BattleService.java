package com.ender.tablettop.service;

import com.ender.tablettop.service.dto.BattleDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Battle.
 */
public interface BattleService {

    /**
     * Save a battle.
     *
     * @param battleDTO the entity to save
     * @return the persisted entity
     */
    BattleDTO save(BattleDTO battleDTO);

    /**
     * Get all the battles.
     *
     * @return the list of entities
     */
    List<BattleDTO> findAll();

    /**
     * Get all the Battle with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<BattleDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" battle.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<BattleDTO> findOne(Long id);

    /**
     * Delete the "id" battle.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

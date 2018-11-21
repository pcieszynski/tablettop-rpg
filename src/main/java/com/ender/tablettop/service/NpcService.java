package com.ender.tablettop.service;

import com.ender.tablettop.service.dto.NpcDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Npc.
 */
public interface NpcService {

    /**
     * Save a npc.
     *
     * @param npcDTO the entity to save
     * @return the persisted entity
     */
    NpcDTO save(NpcDTO npcDTO);

    /**
     * Get all the npcs.
     *
     * @return the list of entities
     */
    List<NpcDTO> findAll();


    /**
     * Get the "id" npc.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<NpcDTO> findOne(Long id);

    /**
     * Delete the "id" npc.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

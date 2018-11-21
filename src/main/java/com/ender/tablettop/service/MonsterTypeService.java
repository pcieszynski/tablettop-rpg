package com.ender.tablettop.service;

import com.ender.tablettop.service.dto.MonsterTypeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing MonsterType.
 */
public interface MonsterTypeService {

    /**
     * Save a monsterType.
     *
     * @param monsterTypeDTO the entity to save
     * @return the persisted entity
     */
    MonsterTypeDTO save(MonsterTypeDTO monsterTypeDTO);

    /**
     * Get all the monsterTypes.
     *
     * @return the list of entities
     */
    List<MonsterTypeDTO> findAll();


    /**
     * Get the "id" monsterType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MonsterTypeDTO> findOne(Long id);

    /**
     * Delete the "id" monsterType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

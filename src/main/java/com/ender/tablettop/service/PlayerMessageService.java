package com.ender.tablettop.service;

import com.ender.tablettop.service.dto.PlayerMessageDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing PlayerMessage.
 */
public interface PlayerMessageService {

    /**
     * Save a playerMessage.
     *
     * @param playerMessageDTO the entity to save
     * @return the persisted entity
     */
    PlayerMessageDTO save(PlayerMessageDTO playerMessageDTO);

    /**
     * Get all the playerMessages.
     *
     * @return the list of entities
     */
    List<PlayerMessageDTO> findAll();


    /**
     * Get the "id" playerMessage.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PlayerMessageDTO> findOne(Long id);

    /**
     * Delete the "id" playerMessage.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

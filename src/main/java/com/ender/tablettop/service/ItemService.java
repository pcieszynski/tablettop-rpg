package com.ender.tablettop.service;

import com.ender.tablettop.service.dto.ItemDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Item.
 */
public interface ItemService {

    /**
     * Save a item.
     *
     * @param itemDTO the entity to save
     * @return the persisted entity
     */
    ItemDTO save(ItemDTO itemDTO);

    /**
     * Get all the items.
     *
     * @return the list of entities
     */
    List<ItemDTO> findAll();


    /**
     * Get the "id" item.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ItemDTO> findOne(Long id);

    /**
     * Delete the "id" item.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

package com.ender.tablettop.service;

import com.ender.tablettop.service.dto.ShopDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Shop.
 */
public interface ShopService {

    /**
     * Save a shop.
     *
     * @param shopDTO the entity to save
     * @return the persisted entity
     */
    ShopDTO save(ShopDTO shopDTO);

    /**
     * Get all the shops.
     *
     * @return the list of entities
     */
    List<ShopDTO> findAll();

    /**
     * Get all the Shop with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<ShopDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" shop.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ShopDTO> findOne(Long id);

    /**
     * Delete the "id" shop.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

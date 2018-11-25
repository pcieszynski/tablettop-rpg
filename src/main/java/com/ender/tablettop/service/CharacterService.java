package com.ender.tablettop.service;

import com.ender.tablettop.service.dto.CharacterDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Character.
 */
public interface CharacterService {

    /**
     * Save a character.
     *
     * @param characterDTO the entity to save
     * @return the persisted entity
     */
    CharacterDTO save(CharacterDTO characterDTO);

    /**
     * Get all the characters.
     *
     * @return the list of entities
     */
    List<CharacterDTO> findAll();

    /**
     * Get all the Character with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<CharacterDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" character.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CharacterDTO> findOne(Long id);

    /**
     * Delete the "id" character.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    CharacterDTO findCharacterByPlayerIdAndGameId(String playerId, String gameId);
}

package com.ender.tablettop.service.impl;

import com.ender.tablettop.service.CharacterService;
import com.ender.tablettop.domain.Character;
import com.ender.tablettop.repository.CharacterRepository;
import com.ender.tablettop.service.dto.CharacterDTO;
import com.ender.tablettop.service.mapper.CharacterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Character.
 */
@Service
@Transactional
public class CharacterServiceImpl implements CharacterService {

    private final Logger log = LoggerFactory.getLogger(CharacterServiceImpl.class);

    private final CharacterRepository characterRepository;

    private final CharacterMapper characterMapper;

    public CharacterServiceImpl(CharacterRepository characterRepository, CharacterMapper characterMapper) {
        this.characterRepository = characterRepository;
        this.characterMapper = characterMapper;
    }

    /**
     * Save a character.
     *
     * @param characterDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CharacterDTO save(CharacterDTO characterDTO) {
        log.debug("Request to save Character : {}", characterDTO);

        Character character = characterMapper.toEntity(characterDTO);
        character = characterRepository.save(character);
        return characterMapper.toDto(character);
    }

    /**
     * Get all the characters.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CharacterDTO> findAll() {
        log.debug("Request to get all Characters");
        return characterRepository.findAllWithEagerRelationships().stream()
            .map(characterMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the Character with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<CharacterDTO> findAllWithEagerRelationships(Pageable pageable) {
        return characterRepository.findAllWithEagerRelationships(pageable).map(characterMapper::toDto);
    }


    /**
     * Get one character by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CharacterDTO> findOne(Long id) {
        log.debug("Request to get Character : {}", id);
        return characterRepository.findOneWithEagerRelationships(id)
            .map(characterMapper::toDto);
    }

    /**
     * Delete the character by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Character : {}", id);
        characterRepository.deleteById(id);
    }

    @Override
    public CharacterDTO findCharacterByPlayerIdAndGameId(String playerId, String gameId) {
        return characterRepository.findByPlayerIdAndGameId(playerId,gameId);
    }
}

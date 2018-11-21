package com.ender.tablettop.service.impl;

import com.ender.tablettop.service.GameService;
import com.ender.tablettop.domain.Game;
import com.ender.tablettop.repository.GameRepository;
import com.ender.tablettop.service.dto.GameDTO;
import com.ender.tablettop.service.mapper.GameMapper;
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
 * Service Implementation for managing Game.
 */
@Service
@Transactional
public class GameServiceImpl implements GameService {

    private final Logger log = LoggerFactory.getLogger(GameServiceImpl.class);

    private final GameRepository gameRepository;

    private final GameMapper gameMapper;

    public GameServiceImpl(GameRepository gameRepository, GameMapper gameMapper) {
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
    }

    /**
     * Save a game.
     *
     * @param gameDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public GameDTO save(GameDTO gameDTO) {
        log.debug("Request to save Game : {}", gameDTO);

        Game game = gameMapper.toEntity(gameDTO);
        game = gameRepository.save(game);
        return gameMapper.toDto(game);
    }

    /**
     * Get all the games.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<GameDTO> findAll() {
        log.debug("Request to get all Games");
        return gameRepository.findAllWithEagerRelationships().stream()
            .map(gameMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the Game with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<GameDTO> findAllWithEagerRelationships(Pageable pageable) {
        return gameRepository.findAllWithEagerRelationships(pageable).map(gameMapper::toDto);
    }
    

    /**
     * Get one game by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<GameDTO> findOne(Long id) {
        log.debug("Request to get Game : {}", id);
        return gameRepository.findOneWithEagerRelationships(id)
            .map(gameMapper::toDto);
    }

    /**
     * Delete the game by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Game : {}", id);
        gameRepository.deleteById(id);
    }
}

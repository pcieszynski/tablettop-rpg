package com.ender.tablettop.service.impl;

import com.ender.tablettop.service.GamemasterService;
import com.ender.tablettop.domain.Gamemaster;
import com.ender.tablettop.repository.GamemasterRepository;
import com.ender.tablettop.service.dto.GamemasterDTO;
import com.ender.tablettop.service.mapper.GamemasterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Gamemaster.
 */
@Service
@Transactional
public class GamemasterServiceImpl implements GamemasterService {

    private final Logger log = LoggerFactory.getLogger(GamemasterServiceImpl.class);

    private final GamemasterRepository gamemasterRepository;

    private final GamemasterMapper gamemasterMapper;

    public GamemasterServiceImpl(GamemasterRepository gamemasterRepository, GamemasterMapper gamemasterMapper) {
        this.gamemasterRepository = gamemasterRepository;
        this.gamemasterMapper = gamemasterMapper;
    }

    /**
     * Save a gamemaster.
     *
     * @param gamemasterDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public GamemasterDTO save(GamemasterDTO gamemasterDTO) {
        log.debug("Request to save Gamemaster : {}", gamemasterDTO);

        Gamemaster gamemaster = gamemasterMapper.toEntity(gamemasterDTO);
        gamemaster = gamemasterRepository.save(gamemaster);
        return gamemasterMapper.toDto(gamemaster);
    }

    /**
     * Get all the gamemasters.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<GamemasterDTO> findAll() {
        log.debug("Request to get all Gamemasters");
        return gamemasterRepository.findAll().stream()
            .map(gamemasterMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one gamemaster by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<GamemasterDTO> findOne(Long id) {
        log.debug("Request to get Gamemaster : {}", id);
        return gamemasterRepository.findById(id)
            .map(gamemasterMapper::toDto);
    }

    /**
     * Delete the gamemaster by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Gamemaster : {}", id);
        gamemasterRepository.deleteById(id);
    }
}

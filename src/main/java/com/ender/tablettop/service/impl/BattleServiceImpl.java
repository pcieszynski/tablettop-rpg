package com.ender.tablettop.service.impl;

import com.ender.tablettop.service.BattleService;
import com.ender.tablettop.domain.Battle;
import com.ender.tablettop.repository.BattleRepository;
import com.ender.tablettop.service.dto.BattleDTO;
import com.ender.tablettop.service.mapper.BattleMapper;
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
 * Service Implementation for managing Battle.
 */
@Service
@Transactional
public class BattleServiceImpl implements BattleService {

    private final Logger log = LoggerFactory.getLogger(BattleServiceImpl.class);

    private final BattleRepository battleRepository;

    private final BattleMapper battleMapper;

    public BattleServiceImpl(BattleRepository battleRepository, BattleMapper battleMapper) {
        this.battleRepository = battleRepository;
        this.battleMapper = battleMapper;
    }

    /**
     * Save a battle.
     *
     * @param battleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BattleDTO save(BattleDTO battleDTO) {
        log.debug("Request to save Battle : {}", battleDTO);

        Battle battle = battleMapper.toEntity(battleDTO);
        battle = battleRepository.save(battle);
        return battleMapper.toDto(battle);
    }

    /**
     * Get all the battles.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BattleDTO> findAll() {
        log.debug("Request to get all Battles");
        return battleRepository.findAllWithEagerRelationships().stream()
            .map(battleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the Battle with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<BattleDTO> findAllWithEagerRelationships(Pageable pageable) {
        return battleRepository.findAllWithEagerRelationships(pageable).map(battleMapper::toDto);
    }
    

    /**
     * Get one battle by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BattleDTO> findOne(Long id) {
        log.debug("Request to get Battle : {}", id);
        return battleRepository.findOneWithEagerRelationships(id)
            .map(battleMapper::toDto);
    }

    /**
     * Delete the battle by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Battle : {}", id);
        battleRepository.deleteById(id);
    }
}

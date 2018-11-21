package com.ender.tablettop.service.impl;

import com.ender.tablettop.service.MonsterService;
import com.ender.tablettop.domain.Monster;
import com.ender.tablettop.repository.MonsterRepository;
import com.ender.tablettop.service.dto.MonsterDTO;
import com.ender.tablettop.service.mapper.MonsterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Monster.
 */
@Service
@Transactional
public class MonsterServiceImpl implements MonsterService {

    private final Logger log = LoggerFactory.getLogger(MonsterServiceImpl.class);

    private final MonsterRepository monsterRepository;

    private final MonsterMapper monsterMapper;

    public MonsterServiceImpl(MonsterRepository monsterRepository, MonsterMapper monsterMapper) {
        this.monsterRepository = monsterRepository;
        this.monsterMapper = monsterMapper;
    }

    /**
     * Save a monster.
     *
     * @param monsterDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MonsterDTO save(MonsterDTO monsterDTO) {
        log.debug("Request to save Monster : {}", monsterDTO);

        Monster monster = monsterMapper.toEntity(monsterDTO);
        monster = monsterRepository.save(monster);
        return monsterMapper.toDto(monster);
    }

    /**
     * Get all the monsters.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<MonsterDTO> findAll() {
        log.debug("Request to get all Monsters");
        return monsterRepository.findAll().stream()
            .map(monsterMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one monster by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MonsterDTO> findOne(Long id) {
        log.debug("Request to get Monster : {}", id);
        return monsterRepository.findById(id)
            .map(monsterMapper::toDto);
    }

    /**
     * Delete the monster by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Monster : {}", id);
        monsterRepository.deleteById(id);
    }
}

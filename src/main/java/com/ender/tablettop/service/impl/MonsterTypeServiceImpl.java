package com.ender.tablettop.service.impl;

import com.ender.tablettop.service.MonsterTypeService;
import com.ender.tablettop.domain.MonsterType;
import com.ender.tablettop.repository.MonsterTypeRepository;
import com.ender.tablettop.service.dto.MonsterTypeDTO;
import com.ender.tablettop.service.mapper.MonsterTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing MonsterType.
 */
@Service
@Transactional
public class MonsterTypeServiceImpl implements MonsterTypeService {

    private final Logger log = LoggerFactory.getLogger(MonsterTypeServiceImpl.class);

    private final MonsterTypeRepository monsterTypeRepository;

    private final MonsterTypeMapper monsterTypeMapper;

    public MonsterTypeServiceImpl(MonsterTypeRepository monsterTypeRepository, MonsterTypeMapper monsterTypeMapper) {
        this.monsterTypeRepository = monsterTypeRepository;
        this.monsterTypeMapper = monsterTypeMapper;
    }

    /**
     * Save a monsterType.
     *
     * @param monsterTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MonsterTypeDTO save(MonsterTypeDTO monsterTypeDTO) {
        log.debug("Request to save MonsterType : {}", monsterTypeDTO);

        MonsterType monsterType = monsterTypeMapper.toEntity(monsterTypeDTO);
        monsterType = monsterTypeRepository.save(monsterType);
        return monsterTypeMapper.toDto(monsterType);
    }

    /**
     * Get all the monsterTypes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<MonsterTypeDTO> findAll() {
        log.debug("Request to get all MonsterTypes");
        return monsterTypeRepository.findAll().stream()
            .map(monsterTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one monsterType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MonsterTypeDTO> findOne(Long id) {
        log.debug("Request to get MonsterType : {}", id);
        return monsterTypeRepository.findById(id)
            .map(monsterTypeMapper::toDto);
    }

    /**
     * Delete the monsterType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MonsterType : {}", id);
        monsterTypeRepository.deleteById(id);
    }
}

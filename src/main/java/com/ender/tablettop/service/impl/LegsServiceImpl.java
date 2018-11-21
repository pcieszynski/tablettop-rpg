package com.ender.tablettop.service.impl;

import com.ender.tablettop.service.LegsService;
import com.ender.tablettop.domain.Legs;
import com.ender.tablettop.repository.LegsRepository;
import com.ender.tablettop.service.dto.LegsDTO;
import com.ender.tablettop.service.mapper.LegsMapper;
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
 * Service Implementation for managing Legs.
 */
@Service
@Transactional
public class LegsServiceImpl implements LegsService {

    private final Logger log = LoggerFactory.getLogger(LegsServiceImpl.class);

    private final LegsRepository legsRepository;

    private final LegsMapper legsMapper;

    public LegsServiceImpl(LegsRepository legsRepository, LegsMapper legsMapper) {
        this.legsRepository = legsRepository;
        this.legsMapper = legsMapper;
    }

    /**
     * Save a legs.
     *
     * @param legsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LegsDTO save(LegsDTO legsDTO) {
        log.debug("Request to save Legs : {}", legsDTO);

        Legs legs = legsMapper.toEntity(legsDTO);
        legs = legsRepository.save(legs);
        return legsMapper.toDto(legs);
    }

    /**
     * Get all the legs.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<LegsDTO> findAll() {
        log.debug("Request to get all Legs");
        return legsRepository.findAllWithEagerRelationships().stream()
            .map(legsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the Legs with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<LegsDTO> findAllWithEagerRelationships(Pageable pageable) {
        return legsRepository.findAllWithEagerRelationships(pageable).map(legsMapper::toDto);
    }
    

    /**
     * Get one legs by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LegsDTO> findOne(Long id) {
        log.debug("Request to get Legs : {}", id);
        return legsRepository.findOneWithEagerRelationships(id)
            .map(legsMapper::toDto);
    }

    /**
     * Delete the legs by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Legs : {}", id);
        legsRepository.deleteById(id);
    }
}

package com.ender.tablettop.service.impl;

import com.ender.tablettop.service.GlovesService;
import com.ender.tablettop.domain.Gloves;
import com.ender.tablettop.repository.GlovesRepository;
import com.ender.tablettop.service.dto.GlovesDTO;
import com.ender.tablettop.service.mapper.GlovesMapper;
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
 * Service Implementation for managing Gloves.
 */
@Service
@Transactional
public class GlovesServiceImpl implements GlovesService {

    private final Logger log = LoggerFactory.getLogger(GlovesServiceImpl.class);

    private final GlovesRepository glovesRepository;

    private final GlovesMapper glovesMapper;

    public GlovesServiceImpl(GlovesRepository glovesRepository, GlovesMapper glovesMapper) {
        this.glovesRepository = glovesRepository;
        this.glovesMapper = glovesMapper;
    }

    /**
     * Save a gloves.
     *
     * @param glovesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public GlovesDTO save(GlovesDTO glovesDTO) {
        log.debug("Request to save Gloves : {}", glovesDTO);

        Gloves gloves = glovesMapper.toEntity(glovesDTO);
        gloves = glovesRepository.save(gloves);
        return glovesMapper.toDto(gloves);
    }

    /**
     * Get all the gloves.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<GlovesDTO> findAll() {
        log.debug("Request to get all Gloves");
        return glovesRepository.findAllWithEagerRelationships().stream()
            .map(glovesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the Gloves with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<GlovesDTO> findAllWithEagerRelationships(Pageable pageable) {
        return glovesRepository.findAllWithEagerRelationships(pageable).map(glovesMapper::toDto);
    }
    

    /**
     * Get one gloves by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<GlovesDTO> findOne(Long id) {
        log.debug("Request to get Gloves : {}", id);
        return glovesRepository.findOneWithEagerRelationships(id)
            .map(glovesMapper::toDto);
    }

    /**
     * Delete the gloves by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Gloves : {}", id);
        glovesRepository.deleteById(id);
    }
}

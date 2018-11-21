package com.ender.tablettop.service.impl;

import com.ender.tablettop.service.LeftHandService;
import com.ender.tablettop.domain.LeftHand;
import com.ender.tablettop.repository.LeftHandRepository;
import com.ender.tablettop.service.dto.LeftHandDTO;
import com.ender.tablettop.service.mapper.LeftHandMapper;
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
 * Service Implementation for managing LeftHand.
 */
@Service
@Transactional
public class LeftHandServiceImpl implements LeftHandService {

    private final Logger log = LoggerFactory.getLogger(LeftHandServiceImpl.class);

    private final LeftHandRepository leftHandRepository;

    private final LeftHandMapper leftHandMapper;

    public LeftHandServiceImpl(LeftHandRepository leftHandRepository, LeftHandMapper leftHandMapper) {
        this.leftHandRepository = leftHandRepository;
        this.leftHandMapper = leftHandMapper;
    }

    /**
     * Save a leftHand.
     *
     * @param leftHandDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LeftHandDTO save(LeftHandDTO leftHandDTO) {
        log.debug("Request to save LeftHand : {}", leftHandDTO);

        LeftHand leftHand = leftHandMapper.toEntity(leftHandDTO);
        leftHand = leftHandRepository.save(leftHand);
        return leftHandMapper.toDto(leftHand);
    }

    /**
     * Get all the leftHands.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<LeftHandDTO> findAll() {
        log.debug("Request to get all LeftHands");
        return leftHandRepository.findAllWithEagerRelationships().stream()
            .map(leftHandMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the LeftHand with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<LeftHandDTO> findAllWithEagerRelationships(Pageable pageable) {
        return leftHandRepository.findAllWithEagerRelationships(pageable).map(leftHandMapper::toDto);
    }
    

    /**
     * Get one leftHand by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LeftHandDTO> findOne(Long id) {
        log.debug("Request to get LeftHand : {}", id);
        return leftHandRepository.findOneWithEagerRelationships(id)
            .map(leftHandMapper::toDto);
    }

    /**
     * Delete the leftHand by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LeftHand : {}", id);
        leftHandRepository.deleteById(id);
    }
}

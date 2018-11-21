package com.ender.tablettop.service.impl;

import com.ender.tablettop.service.RightHandService;
import com.ender.tablettop.domain.RightHand;
import com.ender.tablettop.repository.RightHandRepository;
import com.ender.tablettop.service.dto.RightHandDTO;
import com.ender.tablettop.service.mapper.RightHandMapper;
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
 * Service Implementation for managing RightHand.
 */
@Service
@Transactional
public class RightHandServiceImpl implements RightHandService {

    private final Logger log = LoggerFactory.getLogger(RightHandServiceImpl.class);

    private final RightHandRepository rightHandRepository;

    private final RightHandMapper rightHandMapper;

    public RightHandServiceImpl(RightHandRepository rightHandRepository, RightHandMapper rightHandMapper) {
        this.rightHandRepository = rightHandRepository;
        this.rightHandMapper = rightHandMapper;
    }

    /**
     * Save a rightHand.
     *
     * @param rightHandDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RightHandDTO save(RightHandDTO rightHandDTO) {
        log.debug("Request to save RightHand : {}", rightHandDTO);

        RightHand rightHand = rightHandMapper.toEntity(rightHandDTO);
        rightHand = rightHandRepository.save(rightHand);
        return rightHandMapper.toDto(rightHand);
    }

    /**
     * Get all the rightHands.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RightHandDTO> findAll() {
        log.debug("Request to get all RightHands");
        return rightHandRepository.findAllWithEagerRelationships().stream()
            .map(rightHandMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the RightHand with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<RightHandDTO> findAllWithEagerRelationships(Pageable pageable) {
        return rightHandRepository.findAllWithEagerRelationships(pageable).map(rightHandMapper::toDto);
    }
    

    /**
     * Get one rightHand by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RightHandDTO> findOne(Long id) {
        log.debug("Request to get RightHand : {}", id);
        return rightHandRepository.findOneWithEagerRelationships(id)
            .map(rightHandMapper::toDto);
    }

    /**
     * Delete the rightHand by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RightHand : {}", id);
        rightHandRepository.deleteById(id);
    }
}

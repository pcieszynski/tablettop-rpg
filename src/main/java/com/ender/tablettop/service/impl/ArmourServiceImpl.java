package com.ender.tablettop.service.impl;

import com.ender.tablettop.service.ArmourService;
import com.ender.tablettop.domain.Armour;
import com.ender.tablettop.repository.ArmourRepository;
import com.ender.tablettop.service.dto.ArmourDTO;
import com.ender.tablettop.service.mapper.ArmourMapper;
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
 * Service Implementation for managing Armour.
 */
@Service
@Transactional
public class ArmourServiceImpl implements ArmourService {

    private final Logger log = LoggerFactory.getLogger(ArmourServiceImpl.class);

    private final ArmourRepository armourRepository;

    private final ArmourMapper armourMapper;

    public ArmourServiceImpl(ArmourRepository armourRepository, ArmourMapper armourMapper) {
        this.armourRepository = armourRepository;
        this.armourMapper = armourMapper;
    }

    /**
     * Save a armour.
     *
     * @param armourDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ArmourDTO save(ArmourDTO armourDTO) {
        log.debug("Request to save Armour : {}", armourDTO);

        Armour armour = armourMapper.toEntity(armourDTO);
        armour = armourRepository.save(armour);
        return armourMapper.toDto(armour);
    }

    /**
     * Get all the armours.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ArmourDTO> findAll() {
        log.debug("Request to get all Armours");
        return armourRepository.findAllWithEagerRelationships().stream()
            .map(armourMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the Armour with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<ArmourDTO> findAllWithEagerRelationships(Pageable pageable) {
        return armourRepository.findAllWithEagerRelationships(pageable).map(armourMapper::toDto);
    }
    

    /**
     * Get one armour by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ArmourDTO> findOne(Long id) {
        log.debug("Request to get Armour : {}", id);
        return armourRepository.findOneWithEagerRelationships(id)
            .map(armourMapper::toDto);
    }

    /**
     * Delete the armour by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Armour : {}", id);
        armourRepository.deleteById(id);
    }
}

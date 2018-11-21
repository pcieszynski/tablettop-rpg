package com.ender.tablettop.service.impl;

import com.ender.tablettop.service.BootsService;
import com.ender.tablettop.domain.Boots;
import com.ender.tablettop.repository.BootsRepository;
import com.ender.tablettop.service.dto.BootsDTO;
import com.ender.tablettop.service.mapper.BootsMapper;
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
 * Service Implementation for managing Boots.
 */
@Service
@Transactional
public class BootsServiceImpl implements BootsService {

    private final Logger log = LoggerFactory.getLogger(BootsServiceImpl.class);

    private final BootsRepository bootsRepository;

    private final BootsMapper bootsMapper;

    public BootsServiceImpl(BootsRepository bootsRepository, BootsMapper bootsMapper) {
        this.bootsRepository = bootsRepository;
        this.bootsMapper = bootsMapper;
    }

    /**
     * Save a boots.
     *
     * @param bootsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BootsDTO save(BootsDTO bootsDTO) {
        log.debug("Request to save Boots : {}", bootsDTO);

        Boots boots = bootsMapper.toEntity(bootsDTO);
        boots = bootsRepository.save(boots);
        return bootsMapper.toDto(boots);
    }

    /**
     * Get all the boots.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BootsDTO> findAll() {
        log.debug("Request to get all Boots");
        return bootsRepository.findAllWithEagerRelationships().stream()
            .map(bootsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the Boots with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<BootsDTO> findAllWithEagerRelationships(Pageable pageable) {
        return bootsRepository.findAllWithEagerRelationships(pageable).map(bootsMapper::toDto);
    }
    

    /**
     * Get one boots by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BootsDTO> findOne(Long id) {
        log.debug("Request to get Boots : {}", id);
        return bootsRepository.findOneWithEagerRelationships(id)
            .map(bootsMapper::toDto);
    }

    /**
     * Delete the boots by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Boots : {}", id);
        bootsRepository.deleteById(id);
    }
}

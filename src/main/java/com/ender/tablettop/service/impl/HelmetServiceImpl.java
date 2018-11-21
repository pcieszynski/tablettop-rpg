package com.ender.tablettop.service.impl;

import com.ender.tablettop.service.HelmetService;
import com.ender.tablettop.domain.Helmet;
import com.ender.tablettop.repository.HelmetRepository;
import com.ender.tablettop.service.dto.HelmetDTO;
import com.ender.tablettop.service.mapper.HelmetMapper;
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
 * Service Implementation for managing Helmet.
 */
@Service
@Transactional
public class HelmetServiceImpl implements HelmetService {

    private final Logger log = LoggerFactory.getLogger(HelmetServiceImpl.class);

    private final HelmetRepository helmetRepository;

    private final HelmetMapper helmetMapper;

    public HelmetServiceImpl(HelmetRepository helmetRepository, HelmetMapper helmetMapper) {
        this.helmetRepository = helmetRepository;
        this.helmetMapper = helmetMapper;
    }

    /**
     * Save a helmet.
     *
     * @param helmetDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public HelmetDTO save(HelmetDTO helmetDTO) {
        log.debug("Request to save Helmet : {}", helmetDTO);

        Helmet helmet = helmetMapper.toEntity(helmetDTO);
        helmet = helmetRepository.save(helmet);
        return helmetMapper.toDto(helmet);
    }

    /**
     * Get all the helmets.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<HelmetDTO> findAll() {
        log.debug("Request to get all Helmets");
        return helmetRepository.findAllWithEagerRelationships().stream()
            .map(helmetMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the Helmet with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<HelmetDTO> findAllWithEagerRelationships(Pageable pageable) {
        return helmetRepository.findAllWithEagerRelationships(pageable).map(helmetMapper::toDto);
    }
    

    /**
     * Get one helmet by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<HelmetDTO> findOne(Long id) {
        log.debug("Request to get Helmet : {}", id);
        return helmetRepository.findOneWithEagerRelationships(id)
            .map(helmetMapper::toDto);
    }

    /**
     * Delete the helmet by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Helmet : {}", id);
        helmetRepository.deleteById(id);
    }
}

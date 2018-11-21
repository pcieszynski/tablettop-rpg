package com.ender.tablettop.service.impl;

import com.ender.tablettop.service.ProfessionService;
import com.ender.tablettop.domain.Profession;
import com.ender.tablettop.repository.ProfessionRepository;
import com.ender.tablettop.service.dto.ProfessionDTO;
import com.ender.tablettop.service.mapper.ProfessionMapper;
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
 * Service Implementation for managing Profession.
 */
@Service
@Transactional
public class ProfessionServiceImpl implements ProfessionService {

    private final Logger log = LoggerFactory.getLogger(ProfessionServiceImpl.class);

    private final ProfessionRepository professionRepository;

    private final ProfessionMapper professionMapper;

    public ProfessionServiceImpl(ProfessionRepository professionRepository, ProfessionMapper professionMapper) {
        this.professionRepository = professionRepository;
        this.professionMapper = professionMapper;
    }

    /**
     * Save a profession.
     *
     * @param professionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProfessionDTO save(ProfessionDTO professionDTO) {
        log.debug("Request to save Profession : {}", professionDTO);

        Profession profession = professionMapper.toEntity(professionDTO);
        profession = professionRepository.save(profession);
        return professionMapper.toDto(profession);
    }

    /**
     * Get all the professions.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProfessionDTO> findAll() {
        log.debug("Request to get all Professions");
        return professionRepository.findAllWithEagerRelationships().stream()
            .map(professionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the Profession with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<ProfessionDTO> findAllWithEagerRelationships(Pageable pageable) {
        return professionRepository.findAllWithEagerRelationships(pageable).map(professionMapper::toDto);
    }
    

    /**
     * Get one profession by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProfessionDTO> findOne(Long id) {
        log.debug("Request to get Profession : {}", id);
        return professionRepository.findOneWithEagerRelationships(id)
            .map(professionMapper::toDto);
    }

    /**
     * Delete the profession by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Profession : {}", id);
        professionRepository.deleteById(id);
    }
}

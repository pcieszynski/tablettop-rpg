package com.ender.tablettop.service.impl;

import com.ender.tablettop.service.NpcService;
import com.ender.tablettop.domain.Npc;
import com.ender.tablettop.repository.NpcRepository;
import com.ender.tablettop.service.dto.NpcDTO;
import com.ender.tablettop.service.mapper.NpcMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Npc.
 */
@Service
@Transactional
public class NpcServiceImpl implements NpcService {

    private final Logger log = LoggerFactory.getLogger(NpcServiceImpl.class);

    private final NpcRepository npcRepository;

    private final NpcMapper npcMapper;

    public NpcServiceImpl(NpcRepository npcRepository, NpcMapper npcMapper) {
        this.npcRepository = npcRepository;
        this.npcMapper = npcMapper;
    }

    /**
     * Save a npc.
     *
     * @param npcDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public NpcDTO save(NpcDTO npcDTO) {
        log.debug("Request to save Npc : {}", npcDTO);

        Npc npc = npcMapper.toEntity(npcDTO);
        npc = npcRepository.save(npc);
        return npcMapper.toDto(npc);
    }

    /**
     * Get all the npcs.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<NpcDTO> findAll() {
        log.debug("Request to get all Npcs");
        return npcRepository.findAll().stream()
            .map(npcMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one npc by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<NpcDTO> findOne(Long id) {
        log.debug("Request to get Npc : {}", id);
        return npcRepository.findById(id)
            .map(npcMapper::toDto);
    }

    /**
     * Delete the npc by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Npc : {}", id);
        npcRepository.deleteById(id);
    }
}

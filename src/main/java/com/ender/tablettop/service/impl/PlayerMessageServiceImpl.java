package com.ender.tablettop.service.impl;

import com.ender.tablettop.service.PlayerMessageService;
import com.ender.tablettop.domain.PlayerMessage;
import com.ender.tablettop.repository.PlayerMessageRepository;
import com.ender.tablettop.service.dto.PlayerMessageDTO;
import com.ender.tablettop.service.mapper.PlayerMessageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing PlayerMessage.
 */
@Service
@Transactional
public class PlayerMessageServiceImpl implements PlayerMessageService {

    private final Logger log = LoggerFactory.getLogger(PlayerMessageServiceImpl.class);

    private final PlayerMessageRepository playerMessageRepository;

    private final PlayerMessageMapper playerMessageMapper;

    public PlayerMessageServiceImpl(PlayerMessageRepository playerMessageRepository, PlayerMessageMapper playerMessageMapper) {
        this.playerMessageRepository = playerMessageRepository;
        this.playerMessageMapper = playerMessageMapper;
    }

    /**
     * Save a playerMessage.
     *
     * @param playerMessageDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PlayerMessageDTO save(PlayerMessageDTO playerMessageDTO) {
        log.debug("Request to save PlayerMessage : {}", playerMessageDTO);

        PlayerMessage playerMessage = playerMessageMapper.toEntity(playerMessageDTO);
        playerMessage = playerMessageRepository.save(playerMessage);
        return playerMessageMapper.toDto(playerMessage);
    }

    /**
     * Get all the playerMessages.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PlayerMessageDTO> findAll() {
        log.debug("Request to get all PlayerMessages");
        return playerMessageRepository.findAll().stream()
            .map(playerMessageMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one playerMessage by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PlayerMessageDTO> findOne(Long id) {
        log.debug("Request to get PlayerMessage : {}", id);
        return playerMessageRepository.findById(id)
            .map(playerMessageMapper::toDto);
    }

    /**
     * Delete the playerMessage by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PlayerMessage : {}", id);
        playerMessageRepository.deleteById(id);
    }
}

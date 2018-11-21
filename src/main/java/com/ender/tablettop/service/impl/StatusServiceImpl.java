package com.ender.tablettop.service.impl;

import com.ender.tablettop.service.StatusService;
import com.ender.tablettop.domain.Status;
import com.ender.tablettop.repository.StatusRepository;
import com.ender.tablettop.service.dto.StatusDTO;
import com.ender.tablettop.service.mapper.StatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Status.
 */
@Service
@Transactional
public class StatusServiceImpl implements StatusService {

    private final Logger log = LoggerFactory.getLogger(StatusServiceImpl.class);

    private final StatusRepository statusRepository;

    private final StatusMapper statusMapper;

    public StatusServiceImpl(StatusRepository statusRepository, StatusMapper statusMapper) {
        this.statusRepository = statusRepository;
        this.statusMapper = statusMapper;
    }

    /**
     * Save a status.
     *
     * @param statusDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public StatusDTO save(StatusDTO statusDTO) {
        log.debug("Request to save Status : {}", statusDTO);

        Status status = statusMapper.toEntity(statusDTO);
        status = statusRepository.save(status);
        return statusMapper.toDto(status);
    }

    /**
     * Get all the statuses.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<StatusDTO> findAll() {
        log.debug("Request to get all Statuses");
        return statusRepository.findAll().stream()
            .map(statusMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one status by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StatusDTO> findOne(Long id) {
        log.debug("Request to get Status : {}", id);
        return statusRepository.findById(id)
            .map(statusMapper::toDto);
    }

    /**
     * Delete the status by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Status : {}", id);
        statusRepository.deleteById(id);
    }
}

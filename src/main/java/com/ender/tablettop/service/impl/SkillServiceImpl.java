package com.ender.tablettop.service.impl;

import com.ender.tablettop.service.SkillService;
import com.ender.tablettop.domain.Skill;
import com.ender.tablettop.repository.SkillRepository;
import com.ender.tablettop.service.dto.SkillDTO;
import com.ender.tablettop.service.mapper.SkillMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Skill.
 */
@Service
@Transactional
public class SkillServiceImpl implements SkillService {

    private final Logger log = LoggerFactory.getLogger(SkillServiceImpl.class);

    private final SkillRepository skillRepository;

    private final SkillMapper skillMapper;

    public SkillServiceImpl(SkillRepository skillRepository, SkillMapper skillMapper) {
        this.skillRepository = skillRepository;
        this.skillMapper = skillMapper;
    }

    /**
     * Save a skill.
     *
     * @param skillDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SkillDTO save(SkillDTO skillDTO) {
        log.debug("Request to save Skill : {}", skillDTO);

        Skill skill = skillMapper.toEntity(skillDTO);
        skill = skillRepository.save(skill);
        return skillMapper.toDto(skill);
    }

    /**
     * Get all the skills.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SkillDTO> findAll() {
        log.debug("Request to get all Skills");
        return skillRepository.findAll().stream()
            .map(skillMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one skill by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SkillDTO> findOne(Long id) {
        log.debug("Request to get Skill : {}", id);
        return skillRepository.findById(id)
            .map(skillMapper::toDto);
    }

    /**
     * Delete the skill by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Skill : {}", id);
        skillRepository.deleteById(id);
    }
}

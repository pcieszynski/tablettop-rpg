package com.ender.tablettop.web.rest;

import com.ender.tablettop.TabletTopRpgApp;

import com.ender.tablettop.domain.MonsterType;
import com.ender.tablettop.repository.MonsterTypeRepository;
import com.ender.tablettop.service.MonsterTypeService;
import com.ender.tablettop.service.dto.MonsterTypeDTO;
import com.ender.tablettop.service.mapper.MonsterTypeMapper;
import com.ender.tablettop.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.ender.tablettop.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MonsterTypeResource REST controller.
 *
 * @see MonsterTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TabletTopRpgApp.class)
public class MonsterTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_STRENGTH = 1;
    private static final Integer UPDATED_STRENGTH = 2;

    private static final Integer DEFAULT_AGILITY = 1;
    private static final Integer UPDATED_AGILITY = 2;

    private static final Integer DEFAULT_CONSTITUITION = 1;
    private static final Integer UPDATED_CONSTITUITION = 2;

    private static final Integer DEFAULT_INTELLIGENCE = 1;
    private static final Integer UPDATED_INTELLIGENCE = 2;

    private static final Integer DEFAULT_WILLPOWER = 1;
    private static final Integer UPDATED_WILLPOWER = 2;

    private static final Integer DEFAULT_CHARISMA = 1;
    private static final Integer UPDATED_CHARISMA = 2;

    private static final Integer DEFAULT_HITPOINTS = 1;
    private static final Integer UPDATED_HITPOINTS = 2;

    private static final String DEFAULT_ATTACK = "AAAAAAAAAA";
    private static final String UPDATED_ATTACK = "BBBBBBBBBB";

    private static final Integer DEFAULT_DEFENSE = 1;
    private static final Integer UPDATED_DEFENSE = 2;

    @Autowired
    private MonsterTypeRepository monsterTypeRepository;

    @Autowired
    private MonsterTypeMapper monsterTypeMapper;

    @Autowired
    private MonsterTypeService monsterTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMonsterTypeMockMvc;

    private MonsterType monsterType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MonsterTypeResource monsterTypeResource = new MonsterTypeResource(monsterTypeService);
        this.restMonsterTypeMockMvc = MockMvcBuilders.standaloneSetup(monsterTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MonsterType createEntity(EntityManager em) {
        MonsterType monsterType = new MonsterType()
            .name(DEFAULT_NAME)
            .strength(DEFAULT_STRENGTH)
            .agility(DEFAULT_AGILITY)
            .constituition(DEFAULT_CONSTITUITION)
            .intelligence(DEFAULT_INTELLIGENCE)
            .willpower(DEFAULT_WILLPOWER)
            .charisma(DEFAULT_CHARISMA)
            .hitpoints(DEFAULT_HITPOINTS)
            .attack(DEFAULT_ATTACK)
            .defense(DEFAULT_DEFENSE);
        return monsterType;
    }

    @Before
    public void initTest() {
        monsterType = createEntity(em);
    }

    @Test
    @Transactional
    public void createMonsterType() throws Exception {
        int databaseSizeBeforeCreate = monsterTypeRepository.findAll().size();

        // Create the MonsterType
        MonsterTypeDTO monsterTypeDTO = monsterTypeMapper.toDto(monsterType);
        restMonsterTypeMockMvc.perform(post("/api/monster-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monsterTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the MonsterType in the database
        List<MonsterType> monsterTypeList = monsterTypeRepository.findAll();
        assertThat(monsterTypeList).hasSize(databaseSizeBeforeCreate + 1);
        MonsterType testMonsterType = monsterTypeList.get(monsterTypeList.size() - 1);
        assertThat(testMonsterType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMonsterType.getStrength()).isEqualTo(DEFAULT_STRENGTH);
        assertThat(testMonsterType.getAgility()).isEqualTo(DEFAULT_AGILITY);
        assertThat(testMonsterType.getConstituition()).isEqualTo(DEFAULT_CONSTITUITION);
        assertThat(testMonsterType.getIntelligence()).isEqualTo(DEFAULT_INTELLIGENCE);
        assertThat(testMonsterType.getWillpower()).isEqualTo(DEFAULT_WILLPOWER);
        assertThat(testMonsterType.getCharisma()).isEqualTo(DEFAULT_CHARISMA);
        assertThat(testMonsterType.getHitpoints()).isEqualTo(DEFAULT_HITPOINTS);
        assertThat(testMonsterType.getAttack()).isEqualTo(DEFAULT_ATTACK);
        assertThat(testMonsterType.getDefense()).isEqualTo(DEFAULT_DEFENSE);
    }

    @Test
    @Transactional
    public void createMonsterTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = monsterTypeRepository.findAll().size();

        // Create the MonsterType with an existing ID
        monsterType.setId(1L);
        MonsterTypeDTO monsterTypeDTO = monsterTypeMapper.toDto(monsterType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMonsterTypeMockMvc.perform(post("/api/monster-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monsterTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MonsterType in the database
        List<MonsterType> monsterTypeList = monsterTypeRepository.findAll();
        assertThat(monsterTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMonsterTypes() throws Exception {
        // Initialize the database
        monsterTypeRepository.saveAndFlush(monsterType);

        // Get all the monsterTypeList
        restMonsterTypeMockMvc.perform(get("/api/monster-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(monsterType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].strength").value(hasItem(DEFAULT_STRENGTH)))
            .andExpect(jsonPath("$.[*].agility").value(hasItem(DEFAULT_AGILITY)))
            .andExpect(jsonPath("$.[*].constituition").value(hasItem(DEFAULT_CONSTITUITION)))
            .andExpect(jsonPath("$.[*].intelligence").value(hasItem(DEFAULT_INTELLIGENCE)))
            .andExpect(jsonPath("$.[*].willpower").value(hasItem(DEFAULT_WILLPOWER)))
            .andExpect(jsonPath("$.[*].charisma").value(hasItem(DEFAULT_CHARISMA)))
            .andExpect(jsonPath("$.[*].hitpoints").value(hasItem(DEFAULT_HITPOINTS)))
            .andExpect(jsonPath("$.[*].attack").value(hasItem(DEFAULT_ATTACK.toString())))
            .andExpect(jsonPath("$.[*].defense").value(hasItem(DEFAULT_DEFENSE)));
    }
    
    @Test
    @Transactional
    public void getMonsterType() throws Exception {
        // Initialize the database
        monsterTypeRepository.saveAndFlush(monsterType);

        // Get the monsterType
        restMonsterTypeMockMvc.perform(get("/api/monster-types/{id}", monsterType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(monsterType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.strength").value(DEFAULT_STRENGTH))
            .andExpect(jsonPath("$.agility").value(DEFAULT_AGILITY))
            .andExpect(jsonPath("$.constituition").value(DEFAULT_CONSTITUITION))
            .andExpect(jsonPath("$.intelligence").value(DEFAULT_INTELLIGENCE))
            .andExpect(jsonPath("$.willpower").value(DEFAULT_WILLPOWER))
            .andExpect(jsonPath("$.charisma").value(DEFAULT_CHARISMA))
            .andExpect(jsonPath("$.hitpoints").value(DEFAULT_HITPOINTS))
            .andExpect(jsonPath("$.attack").value(DEFAULT_ATTACK.toString()))
            .andExpect(jsonPath("$.defense").value(DEFAULT_DEFENSE));
    }

    @Test
    @Transactional
    public void getNonExistingMonsterType() throws Exception {
        // Get the monsterType
        restMonsterTypeMockMvc.perform(get("/api/monster-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMonsterType() throws Exception {
        // Initialize the database
        monsterTypeRepository.saveAndFlush(monsterType);

        int databaseSizeBeforeUpdate = monsterTypeRepository.findAll().size();

        // Update the monsterType
        MonsterType updatedMonsterType = monsterTypeRepository.findById(monsterType.getId()).get();
        // Disconnect from session so that the updates on updatedMonsterType are not directly saved in db
        em.detach(updatedMonsterType);
        updatedMonsterType
            .name(UPDATED_NAME)
            .strength(UPDATED_STRENGTH)
            .agility(UPDATED_AGILITY)
            .constituition(UPDATED_CONSTITUITION)
            .intelligence(UPDATED_INTELLIGENCE)
            .willpower(UPDATED_WILLPOWER)
            .charisma(UPDATED_CHARISMA)
            .hitpoints(UPDATED_HITPOINTS)
            .attack(UPDATED_ATTACK)
            .defense(UPDATED_DEFENSE);
        MonsterTypeDTO monsterTypeDTO = monsterTypeMapper.toDto(updatedMonsterType);

        restMonsterTypeMockMvc.perform(put("/api/monster-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monsterTypeDTO)))
            .andExpect(status().isOk());

        // Validate the MonsterType in the database
        List<MonsterType> monsterTypeList = monsterTypeRepository.findAll();
        assertThat(monsterTypeList).hasSize(databaseSizeBeforeUpdate);
        MonsterType testMonsterType = monsterTypeList.get(monsterTypeList.size() - 1);
        assertThat(testMonsterType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMonsterType.getStrength()).isEqualTo(UPDATED_STRENGTH);
        assertThat(testMonsterType.getAgility()).isEqualTo(UPDATED_AGILITY);
        assertThat(testMonsterType.getConstituition()).isEqualTo(UPDATED_CONSTITUITION);
        assertThat(testMonsterType.getIntelligence()).isEqualTo(UPDATED_INTELLIGENCE);
        assertThat(testMonsterType.getWillpower()).isEqualTo(UPDATED_WILLPOWER);
        assertThat(testMonsterType.getCharisma()).isEqualTo(UPDATED_CHARISMA);
        assertThat(testMonsterType.getHitpoints()).isEqualTo(UPDATED_HITPOINTS);
        assertThat(testMonsterType.getAttack()).isEqualTo(UPDATED_ATTACK);
        assertThat(testMonsterType.getDefense()).isEqualTo(UPDATED_DEFENSE);
    }

    @Test
    @Transactional
    public void updateNonExistingMonsterType() throws Exception {
        int databaseSizeBeforeUpdate = monsterTypeRepository.findAll().size();

        // Create the MonsterType
        MonsterTypeDTO monsterTypeDTO = monsterTypeMapper.toDto(monsterType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMonsterTypeMockMvc.perform(put("/api/monster-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monsterTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MonsterType in the database
        List<MonsterType> monsterTypeList = monsterTypeRepository.findAll();
        assertThat(monsterTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMonsterType() throws Exception {
        // Initialize the database
        monsterTypeRepository.saveAndFlush(monsterType);

        int databaseSizeBeforeDelete = monsterTypeRepository.findAll().size();

        // Get the monsterType
        restMonsterTypeMockMvc.perform(delete("/api/monster-types/{id}", monsterType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MonsterType> monsterTypeList = monsterTypeRepository.findAll();
        assertThat(monsterTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MonsterType.class);
        MonsterType monsterType1 = new MonsterType();
        monsterType1.setId(1L);
        MonsterType monsterType2 = new MonsterType();
        monsterType2.setId(monsterType1.getId());
        assertThat(monsterType1).isEqualTo(monsterType2);
        monsterType2.setId(2L);
        assertThat(monsterType1).isNotEqualTo(monsterType2);
        monsterType1.setId(null);
        assertThat(monsterType1).isNotEqualTo(monsterType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MonsterTypeDTO.class);
        MonsterTypeDTO monsterTypeDTO1 = new MonsterTypeDTO();
        monsterTypeDTO1.setId(1L);
        MonsterTypeDTO monsterTypeDTO2 = new MonsterTypeDTO();
        assertThat(monsterTypeDTO1).isNotEqualTo(monsterTypeDTO2);
        monsterTypeDTO2.setId(monsterTypeDTO1.getId());
        assertThat(monsterTypeDTO1).isEqualTo(monsterTypeDTO2);
        monsterTypeDTO2.setId(2L);
        assertThat(monsterTypeDTO1).isNotEqualTo(monsterTypeDTO2);
        monsterTypeDTO1.setId(null);
        assertThat(monsterTypeDTO1).isNotEqualTo(monsterTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(monsterTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(monsterTypeMapper.fromId(null)).isNull();
    }
}

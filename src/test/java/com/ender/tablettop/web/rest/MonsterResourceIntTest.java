package com.ender.tablettop.web.rest;

import com.ender.tablettop.TabletTopRpgApp;

import com.ender.tablettop.domain.Monster;
import com.ender.tablettop.repository.MonsterRepository;
import com.ender.tablettop.service.MonsterService;
import com.ender.tablettop.service.dto.MonsterDTO;
import com.ender.tablettop.service.mapper.MonsterMapper;
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
 * Test class for the MonsterResource REST controller.
 *
 * @see MonsterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TabletTopRpgApp.class)
public class MonsterResourceIntTest {

    private static final Integer DEFAULT_CURRENT_HP = 1;
    private static final Integer UPDATED_CURRENT_HP = 2;

    @Autowired
    private MonsterRepository monsterRepository;

    @Autowired
    private MonsterMapper monsterMapper;

    @Autowired
    private MonsterService monsterService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMonsterMockMvc;

    private Monster monster;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MonsterResource monsterResource = new MonsterResource(monsterService);
        this.restMonsterMockMvc = MockMvcBuilders.standaloneSetup(monsterResource)
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
    public static Monster createEntity(EntityManager em) {
        Monster monster = new Monster()
            .currentHP(DEFAULT_CURRENT_HP);
        return monster;
    }

    @Before
    public void initTest() {
        monster = createEntity(em);
    }

    @Test
    @Transactional
    public void createMonster() throws Exception {
        int databaseSizeBeforeCreate = monsterRepository.findAll().size();

        // Create the Monster
        MonsterDTO monsterDTO = monsterMapper.toDto(monster);
        restMonsterMockMvc.perform(post("/api/monsters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monsterDTO)))
            .andExpect(status().isCreated());

        // Validate the Monster in the database
        List<Monster> monsterList = monsterRepository.findAll();
        assertThat(monsterList).hasSize(databaseSizeBeforeCreate + 1);
        Monster testMonster = monsterList.get(monsterList.size() - 1);
        assertThat(testMonster.getCurrentHP()).isEqualTo(DEFAULT_CURRENT_HP);
    }

    @Test
    @Transactional
    public void createMonsterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = monsterRepository.findAll().size();

        // Create the Monster with an existing ID
        monster.setId(1L);
        MonsterDTO monsterDTO = monsterMapper.toDto(monster);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMonsterMockMvc.perform(post("/api/monsters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monsterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Monster in the database
        List<Monster> monsterList = monsterRepository.findAll();
        assertThat(monsterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMonsters() throws Exception {
        // Initialize the database
        monsterRepository.saveAndFlush(monster);

        // Get all the monsterList
        restMonsterMockMvc.perform(get("/api/monsters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(monster.getId().intValue())))
            .andExpect(jsonPath("$.[*].currentHP").value(hasItem(DEFAULT_CURRENT_HP)));
    }
    
    @Test
    @Transactional
    public void getMonster() throws Exception {
        // Initialize the database
        monsterRepository.saveAndFlush(monster);

        // Get the monster
        restMonsterMockMvc.perform(get("/api/monsters/{id}", monster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(monster.getId().intValue()))
            .andExpect(jsonPath("$.currentHP").value(DEFAULT_CURRENT_HP));
    }

    @Test
    @Transactional
    public void getNonExistingMonster() throws Exception {
        // Get the monster
        restMonsterMockMvc.perform(get("/api/monsters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMonster() throws Exception {
        // Initialize the database
        monsterRepository.saveAndFlush(monster);

        int databaseSizeBeforeUpdate = monsterRepository.findAll().size();

        // Update the monster
        Monster updatedMonster = monsterRepository.findById(monster.getId()).get();
        // Disconnect from session so that the updates on updatedMonster are not directly saved in db
        em.detach(updatedMonster);
        updatedMonster
            .currentHP(UPDATED_CURRENT_HP);
        MonsterDTO monsterDTO = monsterMapper.toDto(updatedMonster);

        restMonsterMockMvc.perform(put("/api/monsters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monsterDTO)))
            .andExpect(status().isOk());

        // Validate the Monster in the database
        List<Monster> monsterList = monsterRepository.findAll();
        assertThat(monsterList).hasSize(databaseSizeBeforeUpdate);
        Monster testMonster = monsterList.get(monsterList.size() - 1);
        assertThat(testMonster.getCurrentHP()).isEqualTo(UPDATED_CURRENT_HP);
    }

    @Test
    @Transactional
    public void updateNonExistingMonster() throws Exception {
        int databaseSizeBeforeUpdate = monsterRepository.findAll().size();

        // Create the Monster
        MonsterDTO monsterDTO = monsterMapper.toDto(monster);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMonsterMockMvc.perform(put("/api/monsters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monsterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Monster in the database
        List<Monster> monsterList = monsterRepository.findAll();
        assertThat(monsterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMonster() throws Exception {
        // Initialize the database
        monsterRepository.saveAndFlush(monster);

        int databaseSizeBeforeDelete = monsterRepository.findAll().size();

        // Get the monster
        restMonsterMockMvc.perform(delete("/api/monsters/{id}", monster.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Monster> monsterList = monsterRepository.findAll();
        assertThat(monsterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Monster.class);
        Monster monster1 = new Monster();
        monster1.setId(1L);
        Monster monster2 = new Monster();
        monster2.setId(monster1.getId());
        assertThat(monster1).isEqualTo(monster2);
        monster2.setId(2L);
        assertThat(monster1).isNotEqualTo(monster2);
        monster1.setId(null);
        assertThat(monster1).isNotEqualTo(monster2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MonsterDTO.class);
        MonsterDTO monsterDTO1 = new MonsterDTO();
        monsterDTO1.setId(1L);
        MonsterDTO monsterDTO2 = new MonsterDTO();
        assertThat(monsterDTO1).isNotEqualTo(monsterDTO2);
        monsterDTO2.setId(monsterDTO1.getId());
        assertThat(monsterDTO1).isEqualTo(monsterDTO2);
        monsterDTO2.setId(2L);
        assertThat(monsterDTO1).isNotEqualTo(monsterDTO2);
        monsterDTO1.setId(null);
        assertThat(monsterDTO1).isNotEqualTo(monsterDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(monsterMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(monsterMapper.fromId(null)).isNull();
    }
}

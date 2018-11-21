package com.ender.tablettop.web.rest;

import com.ender.tablettop.TabletTopRpgApp;

import com.ender.tablettop.domain.Battle;
import com.ender.tablettop.repository.BattleRepository;
import com.ender.tablettop.service.BattleService;
import com.ender.tablettop.service.dto.BattleDTO;
import com.ender.tablettop.service.mapper.BattleMapper;
import com.ender.tablettop.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static com.ender.tablettop.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BattleResource REST controller.
 *
 * @see BattleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TabletTopRpgApp.class)
public class BattleResourceIntTest {

    private static final String DEFAULT_MONSTER = "AAAAAAAAAA";
    private static final String UPDATED_MONSTER = "BBBBBBBBBB";

    private static final Integer DEFAULT_MONSTER_NUMBER = 1;
    private static final Integer UPDATED_MONSTER_NUMBER = 2;

    @Autowired
    private BattleRepository battleRepository;

    @Mock
    private BattleRepository battleRepositoryMock;

    @Autowired
    private BattleMapper battleMapper;

    @Mock
    private BattleService battleServiceMock;

    @Autowired
    private BattleService battleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBattleMockMvc;

    private Battle battle;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BattleResource battleResource = new BattleResource(battleService);
        this.restBattleMockMvc = MockMvcBuilders.standaloneSetup(battleResource)
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
    public static Battle createEntity(EntityManager em) {
        Battle battle = new Battle()
            .monster(DEFAULT_MONSTER)
            .monsterNumber(DEFAULT_MONSTER_NUMBER);
        return battle;
    }

    @Before
    public void initTest() {
        battle = createEntity(em);
    }

    @Test
    @Transactional
    public void createBattle() throws Exception {
        int databaseSizeBeforeCreate = battleRepository.findAll().size();

        // Create the Battle
        BattleDTO battleDTO = battleMapper.toDto(battle);
        restBattleMockMvc.perform(post("/api/battles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(battleDTO)))
            .andExpect(status().isCreated());

        // Validate the Battle in the database
        List<Battle> battleList = battleRepository.findAll();
        assertThat(battleList).hasSize(databaseSizeBeforeCreate + 1);
        Battle testBattle = battleList.get(battleList.size() - 1);
        assertThat(testBattle.getMonster()).isEqualTo(DEFAULT_MONSTER);
        assertThat(testBattle.getMonsterNumber()).isEqualTo(DEFAULT_MONSTER_NUMBER);
    }

    @Test
    @Transactional
    public void createBattleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = battleRepository.findAll().size();

        // Create the Battle with an existing ID
        battle.setId(1L);
        BattleDTO battleDTO = battleMapper.toDto(battle);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBattleMockMvc.perform(post("/api/battles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(battleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Battle in the database
        List<Battle> battleList = battleRepository.findAll();
        assertThat(battleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBattles() throws Exception {
        // Initialize the database
        battleRepository.saveAndFlush(battle);

        // Get all the battleList
        restBattleMockMvc.perform(get("/api/battles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(battle.getId().intValue())))
            .andExpect(jsonPath("$.[*].monster").value(hasItem(DEFAULT_MONSTER.toString())))
            .andExpect(jsonPath("$.[*].monsterNumber").value(hasItem(DEFAULT_MONSTER_NUMBER)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllBattlesWithEagerRelationshipsIsEnabled() throws Exception {
        BattleResource battleResource = new BattleResource(battleServiceMock);
        when(battleServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restBattleMockMvc = MockMvcBuilders.standaloneSetup(battleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restBattleMockMvc.perform(get("/api/battles?eagerload=true"))
        .andExpect(status().isOk());

        verify(battleServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllBattlesWithEagerRelationshipsIsNotEnabled() throws Exception {
        BattleResource battleResource = new BattleResource(battleServiceMock);
            when(battleServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restBattleMockMvc = MockMvcBuilders.standaloneSetup(battleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restBattleMockMvc.perform(get("/api/battles?eagerload=true"))
        .andExpect(status().isOk());

            verify(battleServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getBattle() throws Exception {
        // Initialize the database
        battleRepository.saveAndFlush(battle);

        // Get the battle
        restBattleMockMvc.perform(get("/api/battles/{id}", battle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(battle.getId().intValue()))
            .andExpect(jsonPath("$.monster").value(DEFAULT_MONSTER.toString()))
            .andExpect(jsonPath("$.monsterNumber").value(DEFAULT_MONSTER_NUMBER));
    }

    @Test
    @Transactional
    public void getNonExistingBattle() throws Exception {
        // Get the battle
        restBattleMockMvc.perform(get("/api/battles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBattle() throws Exception {
        // Initialize the database
        battleRepository.saveAndFlush(battle);

        int databaseSizeBeforeUpdate = battleRepository.findAll().size();

        // Update the battle
        Battle updatedBattle = battleRepository.findById(battle.getId()).get();
        // Disconnect from session so that the updates on updatedBattle are not directly saved in db
        em.detach(updatedBattle);
        updatedBattle
            .monster(UPDATED_MONSTER)
            .monsterNumber(UPDATED_MONSTER_NUMBER);
        BattleDTO battleDTO = battleMapper.toDto(updatedBattle);

        restBattleMockMvc.perform(put("/api/battles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(battleDTO)))
            .andExpect(status().isOk());

        // Validate the Battle in the database
        List<Battle> battleList = battleRepository.findAll();
        assertThat(battleList).hasSize(databaseSizeBeforeUpdate);
        Battle testBattle = battleList.get(battleList.size() - 1);
        assertThat(testBattle.getMonster()).isEqualTo(UPDATED_MONSTER);
        assertThat(testBattle.getMonsterNumber()).isEqualTo(UPDATED_MONSTER_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingBattle() throws Exception {
        int databaseSizeBeforeUpdate = battleRepository.findAll().size();

        // Create the Battle
        BattleDTO battleDTO = battleMapper.toDto(battle);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBattleMockMvc.perform(put("/api/battles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(battleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Battle in the database
        List<Battle> battleList = battleRepository.findAll();
        assertThat(battleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBattle() throws Exception {
        // Initialize the database
        battleRepository.saveAndFlush(battle);

        int databaseSizeBeforeDelete = battleRepository.findAll().size();

        // Get the battle
        restBattleMockMvc.perform(delete("/api/battles/{id}", battle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Battle> battleList = battleRepository.findAll();
        assertThat(battleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Battle.class);
        Battle battle1 = new Battle();
        battle1.setId(1L);
        Battle battle2 = new Battle();
        battle2.setId(battle1.getId());
        assertThat(battle1).isEqualTo(battle2);
        battle2.setId(2L);
        assertThat(battle1).isNotEqualTo(battle2);
        battle1.setId(null);
        assertThat(battle1).isNotEqualTo(battle2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BattleDTO.class);
        BattleDTO battleDTO1 = new BattleDTO();
        battleDTO1.setId(1L);
        BattleDTO battleDTO2 = new BattleDTO();
        assertThat(battleDTO1).isNotEqualTo(battleDTO2);
        battleDTO2.setId(battleDTO1.getId());
        assertThat(battleDTO1).isEqualTo(battleDTO2);
        battleDTO2.setId(2L);
        assertThat(battleDTO1).isNotEqualTo(battleDTO2);
        battleDTO1.setId(null);
        assertThat(battleDTO1).isNotEqualTo(battleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(battleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(battleMapper.fromId(null)).isNull();
    }
}

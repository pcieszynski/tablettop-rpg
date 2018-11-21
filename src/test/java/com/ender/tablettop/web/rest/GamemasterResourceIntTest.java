package com.ender.tablettop.web.rest;

import com.ender.tablettop.TabletTopRpgApp;

import com.ender.tablettop.domain.Gamemaster;
import com.ender.tablettop.repository.GamemasterRepository;
import com.ender.tablettop.service.GamemasterService;
import com.ender.tablettop.service.dto.GamemasterDTO;
import com.ender.tablettop.service.mapper.GamemasterMapper;
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
 * Test class for the GamemasterResource REST controller.
 *
 * @see GamemasterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TabletTopRpgApp.class)
public class GamemasterResourceIntTest {

    @Autowired
    private GamemasterRepository gamemasterRepository;

    @Autowired
    private GamemasterMapper gamemasterMapper;

    @Autowired
    private GamemasterService gamemasterService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGamemasterMockMvc;

    private Gamemaster gamemaster;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GamemasterResource gamemasterResource = new GamemasterResource(gamemasterService);
        this.restGamemasterMockMvc = MockMvcBuilders.standaloneSetup(gamemasterResource)
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
    public static Gamemaster createEntity(EntityManager em) {
        Gamemaster gamemaster = new Gamemaster();
        return gamemaster;
    }

    @Before
    public void initTest() {
        gamemaster = createEntity(em);
    }

    @Test
    @Transactional
    public void createGamemaster() throws Exception {
        int databaseSizeBeforeCreate = gamemasterRepository.findAll().size();

        // Create the Gamemaster
        GamemasterDTO gamemasterDTO = gamemasterMapper.toDto(gamemaster);
        restGamemasterMockMvc.perform(post("/api/gamemasters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gamemasterDTO)))
            .andExpect(status().isCreated());

        // Validate the Gamemaster in the database
        List<Gamemaster> gamemasterList = gamemasterRepository.findAll();
        assertThat(gamemasterList).hasSize(databaseSizeBeforeCreate + 1);
        Gamemaster testGamemaster = gamemasterList.get(gamemasterList.size() - 1);
    }

    @Test
    @Transactional
    public void createGamemasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gamemasterRepository.findAll().size();

        // Create the Gamemaster with an existing ID
        gamemaster.setId(1L);
        GamemasterDTO gamemasterDTO = gamemasterMapper.toDto(gamemaster);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGamemasterMockMvc.perform(post("/api/gamemasters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gamemasterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Gamemaster in the database
        List<Gamemaster> gamemasterList = gamemasterRepository.findAll();
        assertThat(gamemasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGamemasters() throws Exception {
        // Initialize the database
        gamemasterRepository.saveAndFlush(gamemaster);

        // Get all the gamemasterList
        restGamemasterMockMvc.perform(get("/api/gamemasters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gamemaster.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getGamemaster() throws Exception {
        // Initialize the database
        gamemasterRepository.saveAndFlush(gamemaster);

        // Get the gamemaster
        restGamemasterMockMvc.perform(get("/api/gamemasters/{id}", gamemaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gamemaster.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGamemaster() throws Exception {
        // Get the gamemaster
        restGamemasterMockMvc.perform(get("/api/gamemasters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGamemaster() throws Exception {
        // Initialize the database
        gamemasterRepository.saveAndFlush(gamemaster);

        int databaseSizeBeforeUpdate = gamemasterRepository.findAll().size();

        // Update the gamemaster
        Gamemaster updatedGamemaster = gamemasterRepository.findById(gamemaster.getId()).get();
        // Disconnect from session so that the updates on updatedGamemaster are not directly saved in db
        em.detach(updatedGamemaster);
        GamemasterDTO gamemasterDTO = gamemasterMapper.toDto(updatedGamemaster);

        restGamemasterMockMvc.perform(put("/api/gamemasters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gamemasterDTO)))
            .andExpect(status().isOk());

        // Validate the Gamemaster in the database
        List<Gamemaster> gamemasterList = gamemasterRepository.findAll();
        assertThat(gamemasterList).hasSize(databaseSizeBeforeUpdate);
        Gamemaster testGamemaster = gamemasterList.get(gamemasterList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingGamemaster() throws Exception {
        int databaseSizeBeforeUpdate = gamemasterRepository.findAll().size();

        // Create the Gamemaster
        GamemasterDTO gamemasterDTO = gamemasterMapper.toDto(gamemaster);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGamemasterMockMvc.perform(put("/api/gamemasters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gamemasterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Gamemaster in the database
        List<Gamemaster> gamemasterList = gamemasterRepository.findAll();
        assertThat(gamemasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGamemaster() throws Exception {
        // Initialize the database
        gamemasterRepository.saveAndFlush(gamemaster);

        int databaseSizeBeforeDelete = gamemasterRepository.findAll().size();

        // Get the gamemaster
        restGamemasterMockMvc.perform(delete("/api/gamemasters/{id}", gamemaster.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Gamemaster> gamemasterList = gamemasterRepository.findAll();
        assertThat(gamemasterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gamemaster.class);
        Gamemaster gamemaster1 = new Gamemaster();
        gamemaster1.setId(1L);
        Gamemaster gamemaster2 = new Gamemaster();
        gamemaster2.setId(gamemaster1.getId());
        assertThat(gamemaster1).isEqualTo(gamemaster2);
        gamemaster2.setId(2L);
        assertThat(gamemaster1).isNotEqualTo(gamemaster2);
        gamemaster1.setId(null);
        assertThat(gamemaster1).isNotEqualTo(gamemaster2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GamemasterDTO.class);
        GamemasterDTO gamemasterDTO1 = new GamemasterDTO();
        gamemasterDTO1.setId(1L);
        GamemasterDTO gamemasterDTO2 = new GamemasterDTO();
        assertThat(gamemasterDTO1).isNotEqualTo(gamemasterDTO2);
        gamemasterDTO2.setId(gamemasterDTO1.getId());
        assertThat(gamemasterDTO1).isEqualTo(gamemasterDTO2);
        gamemasterDTO2.setId(2L);
        assertThat(gamemasterDTO1).isNotEqualTo(gamemasterDTO2);
        gamemasterDTO1.setId(null);
        assertThat(gamemasterDTO1).isNotEqualTo(gamemasterDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(gamemasterMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(gamemasterMapper.fromId(null)).isNull();
    }
}

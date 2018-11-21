package com.ender.tablettop.web.rest;

import com.ender.tablettop.TabletTopRpgApp;

import com.ender.tablettop.domain.PlayerMessage;
import com.ender.tablettop.repository.PlayerMessageRepository;
import com.ender.tablettop.service.PlayerMessageService;
import com.ender.tablettop.service.dto.PlayerMessageDTO;
import com.ender.tablettop.service.mapper.PlayerMessageMapper;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;


import static com.ender.tablettop.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PlayerMessageResource REST controller.
 *
 * @see PlayerMessageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TabletTopRpgApp.class)
public class PlayerMessageResourceIntTest {

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_ATTACK = "AAAAAAAAAA";
    private static final String UPDATED_ATTACK = "BBBBBBBBBB";

    private static final Integer DEFAULT_HEAL = 1;
    private static final Integer UPDATED_HEAL = 2;

    private static final Integer DEFAULT_DIFFICULTY = 1;
    private static final Integer UPDATED_DIFFICULTY = 2;

    private static final Boolean DEFAULT_SUCCESS = false;
    private static final Boolean UPDATED_SUCCESS = true;

    private static final String DEFAULT_ATTRIBUTE = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE = "BBBBBBBBBB";

    @Autowired
    private PlayerMessageRepository playerMessageRepository;

    @Autowired
    private PlayerMessageMapper playerMessageMapper;

    @Autowired
    private PlayerMessageService playerMessageService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPlayerMessageMockMvc;

    private PlayerMessage playerMessage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlayerMessageResource playerMessageResource = new PlayerMessageResource(playerMessageService);
        this.restPlayerMessageMockMvc = MockMvcBuilders.standaloneSetup(playerMessageResource)
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
    public static PlayerMessage createEntity(EntityManager em) {
        PlayerMessage playerMessage = new PlayerMessage()
            .message(DEFAULT_MESSAGE)
            .attack(DEFAULT_ATTACK)
            .heal(DEFAULT_HEAL)
            .difficulty(DEFAULT_DIFFICULTY)
            .success(DEFAULT_SUCCESS)
            .attribute(DEFAULT_ATTRIBUTE);
        return playerMessage;
    }

    @Before
    public void initTest() {
        playerMessage = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlayerMessage() throws Exception {
        int databaseSizeBeforeCreate = playerMessageRepository.findAll().size();

        // Create the PlayerMessage
        PlayerMessageDTO playerMessageDTO = playerMessageMapper.toDto(playerMessage);
        restPlayerMessageMockMvc.perform(post("/api/player-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playerMessageDTO)))
            .andExpect(status().isCreated());

        // Validate the PlayerMessage in the database
        List<PlayerMessage> playerMessageList = playerMessageRepository.findAll();
        assertThat(playerMessageList).hasSize(databaseSizeBeforeCreate + 1);
        PlayerMessage testPlayerMessage = playerMessageList.get(playerMessageList.size() - 1);
        assertThat(testPlayerMessage.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testPlayerMessage.getAttack()).isEqualTo(DEFAULT_ATTACK);
        assertThat(testPlayerMessage.getHeal()).isEqualTo(DEFAULT_HEAL);
        assertThat(testPlayerMessage.getDifficulty()).isEqualTo(DEFAULT_DIFFICULTY);
        assertThat(testPlayerMessage.isSuccess()).isEqualTo(DEFAULT_SUCCESS);
        assertThat(testPlayerMessage.getAttribute()).isEqualTo(DEFAULT_ATTRIBUTE);
    }

    @Test
    @Transactional
    public void createPlayerMessageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = playerMessageRepository.findAll().size();

        // Create the PlayerMessage with an existing ID
        playerMessage.setId(1L);
        PlayerMessageDTO playerMessageDTO = playerMessageMapper.toDto(playerMessage);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlayerMessageMockMvc.perform(post("/api/player-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playerMessageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlayerMessage in the database
        List<PlayerMessage> playerMessageList = playerMessageRepository.findAll();
        assertThat(playerMessageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPlayerMessages() throws Exception {
        // Initialize the database
        playerMessageRepository.saveAndFlush(playerMessage);

        // Get all the playerMessageList
        restPlayerMessageMockMvc.perform(get("/api/player-messages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(playerMessage.getId().intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())))
            .andExpect(jsonPath("$.[*].attack").value(hasItem(DEFAULT_ATTACK.toString())))
            .andExpect(jsonPath("$.[*].heal").value(hasItem(DEFAULT_HEAL)))
            .andExpect(jsonPath("$.[*].difficulty").value(hasItem(DEFAULT_DIFFICULTY)))
            .andExpect(jsonPath("$.[*].success").value(hasItem(DEFAULT_SUCCESS.booleanValue())))
            .andExpect(jsonPath("$.[*].attribute").value(hasItem(DEFAULT_ATTRIBUTE.toString())));
    }
    
    @Test
    @Transactional
    public void getPlayerMessage() throws Exception {
        // Initialize the database
        playerMessageRepository.saveAndFlush(playerMessage);

        // Get the playerMessage
        restPlayerMessageMockMvc.perform(get("/api/player-messages/{id}", playerMessage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(playerMessage.getId().intValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()))
            .andExpect(jsonPath("$.attack").value(DEFAULT_ATTACK.toString()))
            .andExpect(jsonPath("$.heal").value(DEFAULT_HEAL))
            .andExpect(jsonPath("$.difficulty").value(DEFAULT_DIFFICULTY))
            .andExpect(jsonPath("$.success").value(DEFAULT_SUCCESS.booleanValue()))
            .andExpect(jsonPath("$.attribute").value(DEFAULT_ATTRIBUTE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlayerMessage() throws Exception {
        // Get the playerMessage
        restPlayerMessageMockMvc.perform(get("/api/player-messages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlayerMessage() throws Exception {
        // Initialize the database
        playerMessageRepository.saveAndFlush(playerMessage);

        int databaseSizeBeforeUpdate = playerMessageRepository.findAll().size();

        // Update the playerMessage
        PlayerMessage updatedPlayerMessage = playerMessageRepository.findById(playerMessage.getId()).get();
        // Disconnect from session so that the updates on updatedPlayerMessage are not directly saved in db
        em.detach(updatedPlayerMessage);
        updatedPlayerMessage
            .message(UPDATED_MESSAGE)
            .attack(UPDATED_ATTACK)
            .heal(UPDATED_HEAL)
            .difficulty(UPDATED_DIFFICULTY)
            .success(UPDATED_SUCCESS)
            .attribute(UPDATED_ATTRIBUTE);
        PlayerMessageDTO playerMessageDTO = playerMessageMapper.toDto(updatedPlayerMessage);

        restPlayerMessageMockMvc.perform(put("/api/player-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playerMessageDTO)))
            .andExpect(status().isOk());

        // Validate the PlayerMessage in the database
        List<PlayerMessage> playerMessageList = playerMessageRepository.findAll();
        assertThat(playerMessageList).hasSize(databaseSizeBeforeUpdate);
        PlayerMessage testPlayerMessage = playerMessageList.get(playerMessageList.size() - 1);
        assertThat(testPlayerMessage.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testPlayerMessage.getAttack()).isEqualTo(UPDATED_ATTACK);
        assertThat(testPlayerMessage.getHeal()).isEqualTo(UPDATED_HEAL);
        assertThat(testPlayerMessage.getDifficulty()).isEqualTo(UPDATED_DIFFICULTY);
        assertThat(testPlayerMessage.isSuccess()).isEqualTo(UPDATED_SUCCESS);
        assertThat(testPlayerMessage.getAttribute()).isEqualTo(UPDATED_ATTRIBUTE);
    }

    @Test
    @Transactional
    public void updateNonExistingPlayerMessage() throws Exception {
        int databaseSizeBeforeUpdate = playerMessageRepository.findAll().size();

        // Create the PlayerMessage
        PlayerMessageDTO playerMessageDTO = playerMessageMapper.toDto(playerMessage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlayerMessageMockMvc.perform(put("/api/player-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playerMessageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlayerMessage in the database
        List<PlayerMessage> playerMessageList = playerMessageRepository.findAll();
        assertThat(playerMessageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlayerMessage() throws Exception {
        // Initialize the database
        playerMessageRepository.saveAndFlush(playerMessage);

        int databaseSizeBeforeDelete = playerMessageRepository.findAll().size();

        // Get the playerMessage
        restPlayerMessageMockMvc.perform(delete("/api/player-messages/{id}", playerMessage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PlayerMessage> playerMessageList = playerMessageRepository.findAll();
        assertThat(playerMessageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlayerMessage.class);
        PlayerMessage playerMessage1 = new PlayerMessage();
        playerMessage1.setId(1L);
        PlayerMessage playerMessage2 = new PlayerMessage();
        playerMessage2.setId(playerMessage1.getId());
        assertThat(playerMessage1).isEqualTo(playerMessage2);
        playerMessage2.setId(2L);
        assertThat(playerMessage1).isNotEqualTo(playerMessage2);
        playerMessage1.setId(null);
        assertThat(playerMessage1).isNotEqualTo(playerMessage2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlayerMessageDTO.class);
        PlayerMessageDTO playerMessageDTO1 = new PlayerMessageDTO();
        playerMessageDTO1.setId(1L);
        PlayerMessageDTO playerMessageDTO2 = new PlayerMessageDTO();
        assertThat(playerMessageDTO1).isNotEqualTo(playerMessageDTO2);
        playerMessageDTO2.setId(playerMessageDTO1.getId());
        assertThat(playerMessageDTO1).isEqualTo(playerMessageDTO2);
        playerMessageDTO2.setId(2L);
        assertThat(playerMessageDTO1).isNotEqualTo(playerMessageDTO2);
        playerMessageDTO1.setId(null);
        assertThat(playerMessageDTO1).isNotEqualTo(playerMessageDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(playerMessageMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(playerMessageMapper.fromId(null)).isNull();
    }
}

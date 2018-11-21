package com.ender.tablettop.web.rest;

import com.ender.tablettop.TabletTopRpgApp;

import com.ender.tablettop.domain.Game;
import com.ender.tablettop.repository.GameRepository;
import com.ender.tablettop.service.GameService;
import com.ender.tablettop.service.dto.GameDTO;
import com.ender.tablettop.service.mapper.GameMapper;
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
 * Test class for the GameResource REST controller.
 *
 * @see GameResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TabletTopRpgApp.class)
public class GameResourceIntTest {

    private static final Long DEFAULT_CURRENT_EVENT = 1L;
    private static final Long UPDATED_CURRENT_EVENT = 2L;

    private static final String DEFAULT_CURRENT_PLAYER = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_PLAYER = "BBBBBBBBBB";

    @Autowired
    private GameRepository gameRepository;

    @Mock
    private GameRepository gameRepositoryMock;

    @Autowired
    private GameMapper gameMapper;

    @Mock
    private GameService gameServiceMock;

    @Autowired
    private GameService gameService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGameMockMvc;

    private Game game;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GameResource gameResource = new GameResource(gameService);
        this.restGameMockMvc = MockMvcBuilders.standaloneSetup(gameResource)
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
    public static Game createEntity(EntityManager em) {
        Game game = new Game()
            .currentEvent(DEFAULT_CURRENT_EVENT)
            .currentPlayer(DEFAULT_CURRENT_PLAYER);
        return game;
    }

    @Before
    public void initTest() {
        game = createEntity(em);
    }

    @Test
    @Transactional
    public void createGame() throws Exception {
        int databaseSizeBeforeCreate = gameRepository.findAll().size();

        // Create the Game
        GameDTO gameDTO = gameMapper.toDto(game);
        restGameMockMvc.perform(post("/api/games")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gameDTO)))
            .andExpect(status().isCreated());

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeCreate + 1);
        Game testGame = gameList.get(gameList.size() - 1);
        assertThat(testGame.getCurrentEvent()).isEqualTo(DEFAULT_CURRENT_EVENT);
        assertThat(testGame.getCurrentPlayer()).isEqualTo(DEFAULT_CURRENT_PLAYER);
    }

    @Test
    @Transactional
    public void createGameWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gameRepository.findAll().size();

        // Create the Game with an existing ID
        game.setId(1L);
        GameDTO gameDTO = gameMapper.toDto(game);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGameMockMvc.perform(post("/api/games")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gameDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGames() throws Exception {
        // Initialize the database
        gameRepository.saveAndFlush(game);

        // Get all the gameList
        restGameMockMvc.perform(get("/api/games?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(game.getId().intValue())))
            .andExpect(jsonPath("$.[*].currentEvent").value(hasItem(DEFAULT_CURRENT_EVENT.intValue())))
            .andExpect(jsonPath("$.[*].currentPlayer").value(hasItem(DEFAULT_CURRENT_PLAYER.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllGamesWithEagerRelationshipsIsEnabled() throws Exception {
        GameResource gameResource = new GameResource(gameServiceMock);
        when(gameServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restGameMockMvc = MockMvcBuilders.standaloneSetup(gameResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restGameMockMvc.perform(get("/api/games?eagerload=true"))
        .andExpect(status().isOk());

        verify(gameServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllGamesWithEagerRelationshipsIsNotEnabled() throws Exception {
        GameResource gameResource = new GameResource(gameServiceMock);
            when(gameServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restGameMockMvc = MockMvcBuilders.standaloneSetup(gameResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restGameMockMvc.perform(get("/api/games?eagerload=true"))
        .andExpect(status().isOk());

            verify(gameServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getGame() throws Exception {
        // Initialize the database
        gameRepository.saveAndFlush(game);

        // Get the game
        restGameMockMvc.perform(get("/api/games/{id}", game.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(game.getId().intValue()))
            .andExpect(jsonPath("$.currentEvent").value(DEFAULT_CURRENT_EVENT.intValue()))
            .andExpect(jsonPath("$.currentPlayer").value(DEFAULT_CURRENT_PLAYER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGame() throws Exception {
        // Get the game
        restGameMockMvc.perform(get("/api/games/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGame() throws Exception {
        // Initialize the database
        gameRepository.saveAndFlush(game);

        int databaseSizeBeforeUpdate = gameRepository.findAll().size();

        // Update the game
        Game updatedGame = gameRepository.findById(game.getId()).get();
        // Disconnect from session so that the updates on updatedGame are not directly saved in db
        em.detach(updatedGame);
        updatedGame
            .currentEvent(UPDATED_CURRENT_EVENT)
            .currentPlayer(UPDATED_CURRENT_PLAYER);
        GameDTO gameDTO = gameMapper.toDto(updatedGame);

        restGameMockMvc.perform(put("/api/games")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gameDTO)))
            .andExpect(status().isOk());

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeUpdate);
        Game testGame = gameList.get(gameList.size() - 1);
        assertThat(testGame.getCurrentEvent()).isEqualTo(UPDATED_CURRENT_EVENT);
        assertThat(testGame.getCurrentPlayer()).isEqualTo(UPDATED_CURRENT_PLAYER);
    }

    @Test
    @Transactional
    public void updateNonExistingGame() throws Exception {
        int databaseSizeBeforeUpdate = gameRepository.findAll().size();

        // Create the Game
        GameDTO gameDTO = gameMapper.toDto(game);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGameMockMvc.perform(put("/api/games")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gameDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGame() throws Exception {
        // Initialize the database
        gameRepository.saveAndFlush(game);

        int databaseSizeBeforeDelete = gameRepository.findAll().size();

        // Get the game
        restGameMockMvc.perform(delete("/api/games/{id}", game.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Game.class);
        Game game1 = new Game();
        game1.setId(1L);
        Game game2 = new Game();
        game2.setId(game1.getId());
        assertThat(game1).isEqualTo(game2);
        game2.setId(2L);
        assertThat(game1).isNotEqualTo(game2);
        game1.setId(null);
        assertThat(game1).isNotEqualTo(game2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GameDTO.class);
        GameDTO gameDTO1 = new GameDTO();
        gameDTO1.setId(1L);
        GameDTO gameDTO2 = new GameDTO();
        assertThat(gameDTO1).isNotEqualTo(gameDTO2);
        gameDTO2.setId(gameDTO1.getId());
        assertThat(gameDTO1).isEqualTo(gameDTO2);
        gameDTO2.setId(2L);
        assertThat(gameDTO1).isNotEqualTo(gameDTO2);
        gameDTO1.setId(null);
        assertThat(gameDTO1).isNotEqualTo(gameDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(gameMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(gameMapper.fromId(null)).isNull();
    }
}

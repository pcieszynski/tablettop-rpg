package com.ender.tablettop.web.rest;

import com.ender.tablettop.TabletTopRpgApp;

import com.ender.tablettop.domain.Character;
import com.ender.tablettop.repository.CharacterRepository;
import com.ender.tablettop.service.CharacterService;
import com.ender.tablettop.service.dto.CharacterDTO;
import com.ender.tablettop.service.mapper.CharacterMapper;
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
 * Test class for the CharacterResource REST controller.
 *
 * @see CharacterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TabletTopRpgApp.class)
public class CharacterResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_LEVEL = 1;
    private static final Integer UPDATED_LEVEL = 2;

    private static final Integer DEFAULT_EXPERIENCE = 1;
    private static final Integer UPDATED_EXPERIENCE = 2;

    private static final Integer DEFAULT_MAX_HITPOINTS = 1;
    private static final Integer UPDATED_MAX_HITPOINTS = 2;

    private static final Integer DEFAULT_CURRENT_HITPOINTS = 1;
    private static final Integer UPDATED_CURRENT_HITPOINTS = 2;

    private static final Integer DEFAULT_GOLD = 1;
    private static final Integer UPDATED_GOLD = 2;

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

    @Autowired
    private CharacterRepository characterRepository;

    @Mock
    private CharacterRepository characterRepositoryMock;

    @Autowired
    private CharacterMapper characterMapper;

    @Mock
    private CharacterService characterServiceMock;

    @Autowired
    private CharacterService characterService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCharacterMockMvc;

    private Character character;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CharacterResource characterResource = new CharacterResource(characterService);
        this.restCharacterMockMvc = MockMvcBuilders.standaloneSetup(characterResource)
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
    public static Character createEntity(EntityManager em) {
        Character character = new Character()
            .name(DEFAULT_NAME)
            .level(DEFAULT_LEVEL)
            .experience(DEFAULT_EXPERIENCE)
            .maxHitpoints(DEFAULT_MAX_HITPOINTS)
            .currentHitpoints(DEFAULT_CURRENT_HITPOINTS)
            .gold(DEFAULT_GOLD)
            .strength(DEFAULT_STRENGTH)
            .agility(DEFAULT_AGILITY)
            .constituition(DEFAULT_CONSTITUITION)
            .intelligence(DEFAULT_INTELLIGENCE)
            .willpower(DEFAULT_WILLPOWER)
            .charisma(DEFAULT_CHARISMA);
        return character;
    }

    @Before
    public void initTest() {
        character = createEntity(em);
    }

    @Test
    @Transactional
    public void createCharacter() throws Exception {
        int databaseSizeBeforeCreate = characterRepository.findAll().size();

        // Create the Character
        CharacterDTO characterDTO = characterMapper.toDto(character);
        restCharacterMockMvc.perform(post("/api/characters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(characterDTO)))
            .andExpect(status().isCreated());

        // Validate the Character in the database
        List<Character> characterList = characterRepository.findAll();
        assertThat(characterList).hasSize(databaseSizeBeforeCreate + 1);
        Character testCharacter = characterList.get(characterList.size() - 1);
        assertThat(testCharacter.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCharacter.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testCharacter.getExperience()).isEqualTo(DEFAULT_EXPERIENCE);
        assertThat(testCharacter.getMaxHitpoints()).isEqualTo(DEFAULT_MAX_HITPOINTS);
        assertThat(testCharacter.getCurrentHitpoints()).isEqualTo(DEFAULT_CURRENT_HITPOINTS);
        assertThat(testCharacter.getGold()).isEqualTo(DEFAULT_GOLD);
        assertThat(testCharacter.getStrength()).isEqualTo(DEFAULT_STRENGTH);
        assertThat(testCharacter.getAgility()).isEqualTo(DEFAULT_AGILITY);
        assertThat(testCharacter.getConstituition()).isEqualTo(DEFAULT_CONSTITUITION);
        assertThat(testCharacter.getIntelligence()).isEqualTo(DEFAULT_INTELLIGENCE);
        assertThat(testCharacter.getWillpower()).isEqualTo(DEFAULT_WILLPOWER);
        assertThat(testCharacter.getCharisma()).isEqualTo(DEFAULT_CHARISMA);
    }

    @Test
    @Transactional
    public void createCharacterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = characterRepository.findAll().size();

        // Create the Character with an existing ID
        character.setId(1L);
        CharacterDTO characterDTO = characterMapper.toDto(character);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCharacterMockMvc.perform(post("/api/characters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(characterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Character in the database
        List<Character> characterList = characterRepository.findAll();
        assertThat(characterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCharacters() throws Exception {
        // Initialize the database
        characterRepository.saveAndFlush(character);

        // Get all the characterList
        restCharacterMockMvc.perform(get("/api/characters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(character.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].experience").value(hasItem(DEFAULT_EXPERIENCE)))
            .andExpect(jsonPath("$.[*].maxHitpoints").value(hasItem(DEFAULT_MAX_HITPOINTS)))
            .andExpect(jsonPath("$.[*].currentHitpoints").value(hasItem(DEFAULT_CURRENT_HITPOINTS)))
            .andExpect(jsonPath("$.[*].gold").value(hasItem(DEFAULT_GOLD)))
            .andExpect(jsonPath("$.[*].strength").value(hasItem(DEFAULT_STRENGTH)))
            .andExpect(jsonPath("$.[*].agility").value(hasItem(DEFAULT_AGILITY)))
            .andExpect(jsonPath("$.[*].constituition").value(hasItem(DEFAULT_CONSTITUITION)))
            .andExpect(jsonPath("$.[*].intelligence").value(hasItem(DEFAULT_INTELLIGENCE)))
            .andExpect(jsonPath("$.[*].willpower").value(hasItem(DEFAULT_WILLPOWER)))
            .andExpect(jsonPath("$.[*].charisma").value(hasItem(DEFAULT_CHARISMA)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllCharactersWithEagerRelationshipsIsEnabled() throws Exception {
        CharacterResource characterResource = new CharacterResource(characterServiceMock);
        when(characterServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restCharacterMockMvc = MockMvcBuilders.standaloneSetup(characterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restCharacterMockMvc.perform(get("/api/characters?eagerload=true"))
        .andExpect(status().isOk());

        verify(characterServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllCharactersWithEagerRelationshipsIsNotEnabled() throws Exception {
        CharacterResource characterResource = new CharacterResource(characterServiceMock);
            when(characterServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restCharacterMockMvc = MockMvcBuilders.standaloneSetup(characterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restCharacterMockMvc.perform(get("/api/characters?eagerload=true"))
        .andExpect(status().isOk());

            verify(characterServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getCharacter() throws Exception {
        // Initialize the database
        characterRepository.saveAndFlush(character);

        // Get the character
        restCharacterMockMvc.perform(get("/api/characters/{id}", character.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(character.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL))
            .andExpect(jsonPath("$.experience").value(DEFAULT_EXPERIENCE))
            .andExpect(jsonPath("$.maxHitpoints").value(DEFAULT_MAX_HITPOINTS))
            .andExpect(jsonPath("$.currentHitpoints").value(DEFAULT_CURRENT_HITPOINTS))
            .andExpect(jsonPath("$.gold").value(DEFAULT_GOLD))
            .andExpect(jsonPath("$.strength").value(DEFAULT_STRENGTH))
            .andExpect(jsonPath("$.agility").value(DEFAULT_AGILITY))
            .andExpect(jsonPath("$.constituition").value(DEFAULT_CONSTITUITION))
            .andExpect(jsonPath("$.intelligence").value(DEFAULT_INTELLIGENCE))
            .andExpect(jsonPath("$.willpower").value(DEFAULT_WILLPOWER))
            .andExpect(jsonPath("$.charisma").value(DEFAULT_CHARISMA));
    }

    @Test
    @Transactional
    public void getNonExistingCharacter() throws Exception {
        // Get the character
        restCharacterMockMvc.perform(get("/api/characters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCharacter() throws Exception {
        // Initialize the database
        characterRepository.saveAndFlush(character);

        int databaseSizeBeforeUpdate = characterRepository.findAll().size();

        // Update the character
        Character updatedCharacter = characterRepository.findById(character.getId()).get();
        // Disconnect from session so that the updates on updatedCharacter are not directly saved in db
        em.detach(updatedCharacter);
        updatedCharacter
            .name(UPDATED_NAME)
            .level(UPDATED_LEVEL)
            .experience(UPDATED_EXPERIENCE)
            .maxHitpoints(UPDATED_MAX_HITPOINTS)
            .currentHitpoints(UPDATED_CURRENT_HITPOINTS)
            .gold(UPDATED_GOLD)
            .strength(UPDATED_STRENGTH)
            .agility(UPDATED_AGILITY)
            .constituition(UPDATED_CONSTITUITION)
            .intelligence(UPDATED_INTELLIGENCE)
            .willpower(UPDATED_WILLPOWER)
            .charisma(UPDATED_CHARISMA);
        CharacterDTO characterDTO = characterMapper.toDto(updatedCharacter);

        restCharacterMockMvc.perform(put("/api/characters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(characterDTO)))
            .andExpect(status().isOk());

        // Validate the Character in the database
        List<Character> characterList = characterRepository.findAll();
        assertThat(characterList).hasSize(databaseSizeBeforeUpdate);
        Character testCharacter = characterList.get(characterList.size() - 1);
        assertThat(testCharacter.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCharacter.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testCharacter.getExperience()).isEqualTo(UPDATED_EXPERIENCE);
        assertThat(testCharacter.getMaxHitpoints()).isEqualTo(UPDATED_MAX_HITPOINTS);
        assertThat(testCharacter.getCurrentHitpoints()).isEqualTo(UPDATED_CURRENT_HITPOINTS);
        assertThat(testCharacter.getGold()).isEqualTo(UPDATED_GOLD);
        assertThat(testCharacter.getStrength()).isEqualTo(UPDATED_STRENGTH);
        assertThat(testCharacter.getAgility()).isEqualTo(UPDATED_AGILITY);
        assertThat(testCharacter.getConstituition()).isEqualTo(UPDATED_CONSTITUITION);
        assertThat(testCharacter.getIntelligence()).isEqualTo(UPDATED_INTELLIGENCE);
        assertThat(testCharacter.getWillpower()).isEqualTo(UPDATED_WILLPOWER);
        assertThat(testCharacter.getCharisma()).isEqualTo(UPDATED_CHARISMA);
    }

    @Test
    @Transactional
    public void updateNonExistingCharacter() throws Exception {
        int databaseSizeBeforeUpdate = characterRepository.findAll().size();

        // Create the Character
        CharacterDTO characterDTO = characterMapper.toDto(character);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCharacterMockMvc.perform(put("/api/characters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(characterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Character in the database
        List<Character> characterList = characterRepository.findAll();
        assertThat(characterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCharacter() throws Exception {
        // Initialize the database
        characterRepository.saveAndFlush(character);

        int databaseSizeBeforeDelete = characterRepository.findAll().size();

        // Get the character
        restCharacterMockMvc.perform(delete("/api/characters/{id}", character.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Character> characterList = characterRepository.findAll();
        assertThat(characterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Character.class);
        Character character1 = new Character();
        character1.setId(1L);
        Character character2 = new Character();
        character2.setId(character1.getId());
        assertThat(character1).isEqualTo(character2);
        character2.setId(2L);
        assertThat(character1).isNotEqualTo(character2);
        character1.setId(null);
        assertThat(character1).isNotEqualTo(character2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CharacterDTO.class);
        CharacterDTO characterDTO1 = new CharacterDTO();
        characterDTO1.setId(1L);
        CharacterDTO characterDTO2 = new CharacterDTO();
        assertThat(characterDTO1).isNotEqualTo(characterDTO2);
        characterDTO2.setId(characterDTO1.getId());
        assertThat(characterDTO1).isEqualTo(characterDTO2);
        characterDTO2.setId(2L);
        assertThat(characterDTO1).isNotEqualTo(characterDTO2);
        characterDTO1.setId(null);
        assertThat(characterDTO1).isNotEqualTo(characterDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(characterMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(characterMapper.fromId(null)).isNull();
    }
}

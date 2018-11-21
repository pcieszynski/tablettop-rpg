package com.ender.tablettop.web.rest;

import com.ender.tablettop.TabletTopRpgApp;

import com.ender.tablettop.domain.Profession;
import com.ender.tablettop.repository.ProfessionRepository;
import com.ender.tablettop.service.ProfessionService;
import com.ender.tablettop.service.dto.ProfessionDTO;
import com.ender.tablettop.service.mapper.ProfessionMapper;
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
 * Test class for the ProfessionResource REST controller.
 *
 * @see ProfessionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TabletTopRpgApp.class)
public class ProfessionResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_STRENGTH_MODIFIER = 1;
    private static final Integer UPDATED_STRENGTH_MODIFIER = 2;

    private static final Integer DEFAULT_AGILITY_MODIFIER = 1;
    private static final Integer UPDATED_AGILITY_MODIFIER = 2;

    private static final Integer DEFAULT_CONSTITUITION_MODIFIER = 1;
    private static final Integer UPDATED_CONSTITUITION_MODIFIER = 2;

    private static final Integer DEFAULT_INTELLIGENCE_MODIFIER = 1;
    private static final Integer UPDATED_INTELLIGENCE_MODIFIER = 2;

    private static final Integer DEFAULT_WILLPOWER_MODIFIER = 1;
    private static final Integer UPDATED_WILLPOWER_MODIFIER = 2;

    private static final Integer DEFAULT_CHARISMA_MODIFIER = 1;
    private static final Integer UPDATED_CHARISMA_MODIFIER = 2;

    @Autowired
    private ProfessionRepository professionRepository;

    @Mock
    private ProfessionRepository professionRepositoryMock;

    @Autowired
    private ProfessionMapper professionMapper;

    @Mock
    private ProfessionService professionServiceMock;

    @Autowired
    private ProfessionService professionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProfessionMockMvc;

    private Profession profession;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProfessionResource professionResource = new ProfessionResource(professionService);
        this.restProfessionMockMvc = MockMvcBuilders.standaloneSetup(professionResource)
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
    public static Profession createEntity(EntityManager em) {
        Profession profession = new Profession()
            .name(DEFAULT_NAME)
            .strengthModifier(DEFAULT_STRENGTH_MODIFIER)
            .agilityModifier(DEFAULT_AGILITY_MODIFIER)
            .constituitionModifier(DEFAULT_CONSTITUITION_MODIFIER)
            .intelligenceModifier(DEFAULT_INTELLIGENCE_MODIFIER)
            .willpowerModifier(DEFAULT_WILLPOWER_MODIFIER)
            .charismaModifier(DEFAULT_CHARISMA_MODIFIER);
        return profession;
    }

    @Before
    public void initTest() {
        profession = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfession() throws Exception {
        int databaseSizeBeforeCreate = professionRepository.findAll().size();

        // Create the Profession
        ProfessionDTO professionDTO = professionMapper.toDto(profession);
        restProfessionMockMvc.perform(post("/api/professions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(professionDTO)))
            .andExpect(status().isCreated());

        // Validate the Profession in the database
        List<Profession> professionList = professionRepository.findAll();
        assertThat(professionList).hasSize(databaseSizeBeforeCreate + 1);
        Profession testProfession = professionList.get(professionList.size() - 1);
        assertThat(testProfession.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProfession.getStrengthModifier()).isEqualTo(DEFAULT_STRENGTH_MODIFIER);
        assertThat(testProfession.getAgilityModifier()).isEqualTo(DEFAULT_AGILITY_MODIFIER);
        assertThat(testProfession.getConstituitionModifier()).isEqualTo(DEFAULT_CONSTITUITION_MODIFIER);
        assertThat(testProfession.getIntelligenceModifier()).isEqualTo(DEFAULT_INTELLIGENCE_MODIFIER);
        assertThat(testProfession.getWillpowerModifier()).isEqualTo(DEFAULT_WILLPOWER_MODIFIER);
        assertThat(testProfession.getCharismaModifier()).isEqualTo(DEFAULT_CHARISMA_MODIFIER);
    }

    @Test
    @Transactional
    public void createProfessionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = professionRepository.findAll().size();

        // Create the Profession with an existing ID
        profession.setId(1L);
        ProfessionDTO professionDTO = professionMapper.toDto(profession);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfessionMockMvc.perform(post("/api/professions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(professionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Profession in the database
        List<Profession> professionList = professionRepository.findAll();
        assertThat(professionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProfessions() throws Exception {
        // Initialize the database
        professionRepository.saveAndFlush(profession);

        // Get all the professionList
        restProfessionMockMvc.perform(get("/api/professions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profession.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].strengthModifier").value(hasItem(DEFAULT_STRENGTH_MODIFIER)))
            .andExpect(jsonPath("$.[*].agilityModifier").value(hasItem(DEFAULT_AGILITY_MODIFIER)))
            .andExpect(jsonPath("$.[*].constituitionModifier").value(hasItem(DEFAULT_CONSTITUITION_MODIFIER)))
            .andExpect(jsonPath("$.[*].intelligenceModifier").value(hasItem(DEFAULT_INTELLIGENCE_MODIFIER)))
            .andExpect(jsonPath("$.[*].willpowerModifier").value(hasItem(DEFAULT_WILLPOWER_MODIFIER)))
            .andExpect(jsonPath("$.[*].charismaModifier").value(hasItem(DEFAULT_CHARISMA_MODIFIER)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllProfessionsWithEagerRelationshipsIsEnabled() throws Exception {
        ProfessionResource professionResource = new ProfessionResource(professionServiceMock);
        when(professionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restProfessionMockMvc = MockMvcBuilders.standaloneSetup(professionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restProfessionMockMvc.perform(get("/api/professions?eagerload=true"))
        .andExpect(status().isOk());

        verify(professionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllProfessionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        ProfessionResource professionResource = new ProfessionResource(professionServiceMock);
            when(professionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restProfessionMockMvc = MockMvcBuilders.standaloneSetup(professionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restProfessionMockMvc.perform(get("/api/professions?eagerload=true"))
        .andExpect(status().isOk());

            verify(professionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getProfession() throws Exception {
        // Initialize the database
        professionRepository.saveAndFlush(profession);

        // Get the profession
        restProfessionMockMvc.perform(get("/api/professions/{id}", profession.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(profession.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.strengthModifier").value(DEFAULT_STRENGTH_MODIFIER))
            .andExpect(jsonPath("$.agilityModifier").value(DEFAULT_AGILITY_MODIFIER))
            .andExpect(jsonPath("$.constituitionModifier").value(DEFAULT_CONSTITUITION_MODIFIER))
            .andExpect(jsonPath("$.intelligenceModifier").value(DEFAULT_INTELLIGENCE_MODIFIER))
            .andExpect(jsonPath("$.willpowerModifier").value(DEFAULT_WILLPOWER_MODIFIER))
            .andExpect(jsonPath("$.charismaModifier").value(DEFAULT_CHARISMA_MODIFIER));
    }

    @Test
    @Transactional
    public void getNonExistingProfession() throws Exception {
        // Get the profession
        restProfessionMockMvc.perform(get("/api/professions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfession() throws Exception {
        // Initialize the database
        professionRepository.saveAndFlush(profession);

        int databaseSizeBeforeUpdate = professionRepository.findAll().size();

        // Update the profession
        Profession updatedProfession = professionRepository.findById(profession.getId()).get();
        // Disconnect from session so that the updates on updatedProfession are not directly saved in db
        em.detach(updatedProfession);
        updatedProfession
            .name(UPDATED_NAME)
            .strengthModifier(UPDATED_STRENGTH_MODIFIER)
            .agilityModifier(UPDATED_AGILITY_MODIFIER)
            .constituitionModifier(UPDATED_CONSTITUITION_MODIFIER)
            .intelligenceModifier(UPDATED_INTELLIGENCE_MODIFIER)
            .willpowerModifier(UPDATED_WILLPOWER_MODIFIER)
            .charismaModifier(UPDATED_CHARISMA_MODIFIER);
        ProfessionDTO professionDTO = professionMapper.toDto(updatedProfession);

        restProfessionMockMvc.perform(put("/api/professions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(professionDTO)))
            .andExpect(status().isOk());

        // Validate the Profession in the database
        List<Profession> professionList = professionRepository.findAll();
        assertThat(professionList).hasSize(databaseSizeBeforeUpdate);
        Profession testProfession = professionList.get(professionList.size() - 1);
        assertThat(testProfession.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProfession.getStrengthModifier()).isEqualTo(UPDATED_STRENGTH_MODIFIER);
        assertThat(testProfession.getAgilityModifier()).isEqualTo(UPDATED_AGILITY_MODIFIER);
        assertThat(testProfession.getConstituitionModifier()).isEqualTo(UPDATED_CONSTITUITION_MODIFIER);
        assertThat(testProfession.getIntelligenceModifier()).isEqualTo(UPDATED_INTELLIGENCE_MODIFIER);
        assertThat(testProfession.getWillpowerModifier()).isEqualTo(UPDATED_WILLPOWER_MODIFIER);
        assertThat(testProfession.getCharismaModifier()).isEqualTo(UPDATED_CHARISMA_MODIFIER);
    }

    @Test
    @Transactional
    public void updateNonExistingProfession() throws Exception {
        int databaseSizeBeforeUpdate = professionRepository.findAll().size();

        // Create the Profession
        ProfessionDTO professionDTO = professionMapper.toDto(profession);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfessionMockMvc.perform(put("/api/professions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(professionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Profession in the database
        List<Profession> professionList = professionRepository.findAll();
        assertThat(professionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProfession() throws Exception {
        // Initialize the database
        professionRepository.saveAndFlush(profession);

        int databaseSizeBeforeDelete = professionRepository.findAll().size();

        // Get the profession
        restProfessionMockMvc.perform(delete("/api/professions/{id}", profession.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Profession> professionList = professionRepository.findAll();
        assertThat(professionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Profession.class);
        Profession profession1 = new Profession();
        profession1.setId(1L);
        Profession profession2 = new Profession();
        profession2.setId(profession1.getId());
        assertThat(profession1).isEqualTo(profession2);
        profession2.setId(2L);
        assertThat(profession1).isNotEqualTo(profession2);
        profession1.setId(null);
        assertThat(profession1).isNotEqualTo(profession2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfessionDTO.class);
        ProfessionDTO professionDTO1 = new ProfessionDTO();
        professionDTO1.setId(1L);
        ProfessionDTO professionDTO2 = new ProfessionDTO();
        assertThat(professionDTO1).isNotEqualTo(professionDTO2);
        professionDTO2.setId(professionDTO1.getId());
        assertThat(professionDTO1).isEqualTo(professionDTO2);
        professionDTO2.setId(2L);
        assertThat(professionDTO1).isNotEqualTo(professionDTO2);
        professionDTO1.setId(null);
        assertThat(professionDTO1).isNotEqualTo(professionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(professionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(professionMapper.fromId(null)).isNull();
    }
}

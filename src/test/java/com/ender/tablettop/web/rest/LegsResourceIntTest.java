package com.ender.tablettop.web.rest;

import com.ender.tablettop.TabletTopRpgApp;

import com.ender.tablettop.domain.Legs;
import com.ender.tablettop.repository.LegsRepository;
import com.ender.tablettop.service.LegsService;
import com.ender.tablettop.service.dto.LegsDTO;
import com.ender.tablettop.service.mapper.LegsMapper;
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
 * Test class for the LegsResource REST controller.
 *
 * @see LegsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TabletTopRpgApp.class)
public class LegsResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EFFECT = "AAAAAAAAAA";
    private static final String UPDATED_EFFECT = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRICE = 1;
    private static final Integer UPDATED_PRICE = 2;

    private static final Integer DEFAULT_DEFENCE = 1;
    private static final Integer UPDATED_DEFENCE = 2;

    private static final String DEFAULT_PART = "AAAAAAAAAA";
    private static final String UPDATED_PART = "BBBBBBBBBB";

    @Autowired
    private LegsRepository legsRepository;

    @Mock
    private LegsRepository legsRepositoryMock;

    @Autowired
    private LegsMapper legsMapper;

    @Mock
    private LegsService legsServiceMock;

    @Autowired
    private LegsService legsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLegsMockMvc;

    private Legs legs;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LegsResource legsResource = new LegsResource(legsService);
        this.restLegsMockMvc = MockMvcBuilders.standaloneSetup(legsResource)
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
    public static Legs createEntity(EntityManager em) {
        Legs legs = new Legs()
            .name(DEFAULT_NAME)
            .effect(DEFAULT_EFFECT)
            .price(DEFAULT_PRICE)
            .defence(DEFAULT_DEFENCE)
            .part(DEFAULT_PART);
        return legs;
    }

    @Before
    public void initTest() {
        legs = createEntity(em);
    }

    @Test
    @Transactional
    public void createLegs() throws Exception {
        int databaseSizeBeforeCreate = legsRepository.findAll().size();

        // Create the Legs
        LegsDTO legsDTO = legsMapper.toDto(legs);
        restLegsMockMvc.perform(post("/api/legs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(legsDTO)))
            .andExpect(status().isCreated());

        // Validate the Legs in the database
        List<Legs> legsList = legsRepository.findAll();
        assertThat(legsList).hasSize(databaseSizeBeforeCreate + 1);
        Legs testLegs = legsList.get(legsList.size() - 1);
        assertThat(testLegs.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLegs.getEffect()).isEqualTo(DEFAULT_EFFECT);
        assertThat(testLegs.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testLegs.getDefence()).isEqualTo(DEFAULT_DEFENCE);
        assertThat(testLegs.getPart()).isEqualTo(DEFAULT_PART);
    }

    @Test
    @Transactional
    public void createLegsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = legsRepository.findAll().size();

        // Create the Legs with an existing ID
        legs.setId(1L);
        LegsDTO legsDTO = legsMapper.toDto(legs);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLegsMockMvc.perform(post("/api/legs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(legsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Legs in the database
        List<Legs> legsList = legsRepository.findAll();
        assertThat(legsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLegs() throws Exception {
        // Initialize the database
        legsRepository.saveAndFlush(legs);

        // Get all the legsList
        restLegsMockMvc.perform(get("/api/legs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(legs.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].effect").value(hasItem(DEFAULT_EFFECT.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].defence").value(hasItem(DEFAULT_DEFENCE)))
            .andExpect(jsonPath("$.[*].part").value(hasItem(DEFAULT_PART.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllLegsWithEagerRelationshipsIsEnabled() throws Exception {
        LegsResource legsResource = new LegsResource(legsServiceMock);
        when(legsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restLegsMockMvc = MockMvcBuilders.standaloneSetup(legsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restLegsMockMvc.perform(get("/api/legs?eagerload=true"))
        .andExpect(status().isOk());

        verify(legsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllLegsWithEagerRelationshipsIsNotEnabled() throws Exception {
        LegsResource legsResource = new LegsResource(legsServiceMock);
            when(legsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restLegsMockMvc = MockMvcBuilders.standaloneSetup(legsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restLegsMockMvc.perform(get("/api/legs?eagerload=true"))
        .andExpect(status().isOk());

            verify(legsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getLegs() throws Exception {
        // Initialize the database
        legsRepository.saveAndFlush(legs);

        // Get the legs
        restLegsMockMvc.perform(get("/api/legs/{id}", legs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(legs.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.effect").value(DEFAULT_EFFECT.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.defence").value(DEFAULT_DEFENCE))
            .andExpect(jsonPath("$.part").value(DEFAULT_PART.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLegs() throws Exception {
        // Get the legs
        restLegsMockMvc.perform(get("/api/legs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLegs() throws Exception {
        // Initialize the database
        legsRepository.saveAndFlush(legs);

        int databaseSizeBeforeUpdate = legsRepository.findAll().size();

        // Update the legs
        Legs updatedLegs = legsRepository.findById(legs.getId()).get();
        // Disconnect from session so that the updates on updatedLegs are not directly saved in db
        em.detach(updatedLegs);
        updatedLegs
            .name(UPDATED_NAME)
            .effect(UPDATED_EFFECT)
            .price(UPDATED_PRICE)
            .defence(UPDATED_DEFENCE)
            .part(UPDATED_PART);
        LegsDTO legsDTO = legsMapper.toDto(updatedLegs);

        restLegsMockMvc.perform(put("/api/legs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(legsDTO)))
            .andExpect(status().isOk());

        // Validate the Legs in the database
        List<Legs> legsList = legsRepository.findAll();
        assertThat(legsList).hasSize(databaseSizeBeforeUpdate);
        Legs testLegs = legsList.get(legsList.size() - 1);
        assertThat(testLegs.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLegs.getEffect()).isEqualTo(UPDATED_EFFECT);
        assertThat(testLegs.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testLegs.getDefence()).isEqualTo(UPDATED_DEFENCE);
        assertThat(testLegs.getPart()).isEqualTo(UPDATED_PART);
    }

    @Test
    @Transactional
    public void updateNonExistingLegs() throws Exception {
        int databaseSizeBeforeUpdate = legsRepository.findAll().size();

        // Create the Legs
        LegsDTO legsDTO = legsMapper.toDto(legs);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLegsMockMvc.perform(put("/api/legs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(legsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Legs in the database
        List<Legs> legsList = legsRepository.findAll();
        assertThat(legsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLegs() throws Exception {
        // Initialize the database
        legsRepository.saveAndFlush(legs);

        int databaseSizeBeforeDelete = legsRepository.findAll().size();

        // Get the legs
        restLegsMockMvc.perform(delete("/api/legs/{id}", legs.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Legs> legsList = legsRepository.findAll();
        assertThat(legsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Legs.class);
        Legs legs1 = new Legs();
        legs1.setId(1L);
        Legs legs2 = new Legs();
        legs2.setId(legs1.getId());
        assertThat(legs1).isEqualTo(legs2);
        legs2.setId(2L);
        assertThat(legs1).isNotEqualTo(legs2);
        legs1.setId(null);
        assertThat(legs1).isNotEqualTo(legs2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LegsDTO.class);
        LegsDTO legsDTO1 = new LegsDTO();
        legsDTO1.setId(1L);
        LegsDTO legsDTO2 = new LegsDTO();
        assertThat(legsDTO1).isNotEqualTo(legsDTO2);
        legsDTO2.setId(legsDTO1.getId());
        assertThat(legsDTO1).isEqualTo(legsDTO2);
        legsDTO2.setId(2L);
        assertThat(legsDTO1).isNotEqualTo(legsDTO2);
        legsDTO1.setId(null);
        assertThat(legsDTO1).isNotEqualTo(legsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(legsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(legsMapper.fromId(null)).isNull();
    }
}

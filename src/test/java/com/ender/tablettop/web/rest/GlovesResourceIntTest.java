package com.ender.tablettop.web.rest;

import com.ender.tablettop.TabletTopRpgApp;

import com.ender.tablettop.domain.Gloves;
import com.ender.tablettop.repository.GlovesRepository;
import com.ender.tablettop.service.GlovesService;
import com.ender.tablettop.service.dto.GlovesDTO;
import com.ender.tablettop.service.mapper.GlovesMapper;
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
 * Test class for the GlovesResource REST controller.
 *
 * @see GlovesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TabletTopRpgApp.class)
public class GlovesResourceIntTest {

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
    private GlovesRepository glovesRepository;

    @Mock
    private GlovesRepository glovesRepositoryMock;

    @Autowired
    private GlovesMapper glovesMapper;

    @Mock
    private GlovesService glovesServiceMock;

    @Autowired
    private GlovesService glovesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGlovesMockMvc;

    private Gloves gloves;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GlovesResource glovesResource = new GlovesResource(glovesService);
        this.restGlovesMockMvc = MockMvcBuilders.standaloneSetup(glovesResource)
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
    public static Gloves createEntity(EntityManager em) {
        Gloves gloves = new Gloves()
            .name(DEFAULT_NAME)
            .effect(DEFAULT_EFFECT)
            .price(DEFAULT_PRICE)
            .defence(DEFAULT_DEFENCE)
            .part(DEFAULT_PART);
        return gloves;
    }

    @Before
    public void initTest() {
        gloves = createEntity(em);
    }

    @Test
    @Transactional
    public void createGloves() throws Exception {
        int databaseSizeBeforeCreate = glovesRepository.findAll().size();

        // Create the Gloves
        GlovesDTO glovesDTO = glovesMapper.toDto(gloves);
        restGlovesMockMvc.perform(post("/api/gloves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(glovesDTO)))
            .andExpect(status().isCreated());

        // Validate the Gloves in the database
        List<Gloves> glovesList = glovesRepository.findAll();
        assertThat(glovesList).hasSize(databaseSizeBeforeCreate + 1);
        Gloves testGloves = glovesList.get(glovesList.size() - 1);
        assertThat(testGloves.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGloves.getEffect()).isEqualTo(DEFAULT_EFFECT);
        assertThat(testGloves.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testGloves.getDefence()).isEqualTo(DEFAULT_DEFENCE);
        assertThat(testGloves.getPart()).isEqualTo(DEFAULT_PART);
    }

    @Test
    @Transactional
    public void createGlovesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = glovesRepository.findAll().size();

        // Create the Gloves with an existing ID
        gloves.setId(1L);
        GlovesDTO glovesDTO = glovesMapper.toDto(gloves);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGlovesMockMvc.perform(post("/api/gloves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(glovesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Gloves in the database
        List<Gloves> glovesList = glovesRepository.findAll();
        assertThat(glovesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGloves() throws Exception {
        // Initialize the database
        glovesRepository.saveAndFlush(gloves);

        // Get all the glovesList
        restGlovesMockMvc.perform(get("/api/gloves?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gloves.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].effect").value(hasItem(DEFAULT_EFFECT.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].defence").value(hasItem(DEFAULT_DEFENCE)))
            .andExpect(jsonPath("$.[*].part").value(hasItem(DEFAULT_PART.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllGlovesWithEagerRelationshipsIsEnabled() throws Exception {
        GlovesResource glovesResource = new GlovesResource(glovesServiceMock);
        when(glovesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restGlovesMockMvc = MockMvcBuilders.standaloneSetup(glovesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restGlovesMockMvc.perform(get("/api/gloves?eagerload=true"))
        .andExpect(status().isOk());

        verify(glovesServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllGlovesWithEagerRelationshipsIsNotEnabled() throws Exception {
        GlovesResource glovesResource = new GlovesResource(glovesServiceMock);
            when(glovesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restGlovesMockMvc = MockMvcBuilders.standaloneSetup(glovesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restGlovesMockMvc.perform(get("/api/gloves?eagerload=true"))
        .andExpect(status().isOk());

            verify(glovesServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getGloves() throws Exception {
        // Initialize the database
        glovesRepository.saveAndFlush(gloves);

        // Get the gloves
        restGlovesMockMvc.perform(get("/api/gloves/{id}", gloves.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gloves.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.effect").value(DEFAULT_EFFECT.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.defence").value(DEFAULT_DEFENCE))
            .andExpect(jsonPath("$.part").value(DEFAULT_PART.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGloves() throws Exception {
        // Get the gloves
        restGlovesMockMvc.perform(get("/api/gloves/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGloves() throws Exception {
        // Initialize the database
        glovesRepository.saveAndFlush(gloves);

        int databaseSizeBeforeUpdate = glovesRepository.findAll().size();

        // Update the gloves
        Gloves updatedGloves = glovesRepository.findById(gloves.getId()).get();
        // Disconnect from session so that the updates on updatedGloves are not directly saved in db
        em.detach(updatedGloves);
        updatedGloves
            .name(UPDATED_NAME)
            .effect(UPDATED_EFFECT)
            .price(UPDATED_PRICE)
            .defence(UPDATED_DEFENCE)
            .part(UPDATED_PART);
        GlovesDTO glovesDTO = glovesMapper.toDto(updatedGloves);

        restGlovesMockMvc.perform(put("/api/gloves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(glovesDTO)))
            .andExpect(status().isOk());

        // Validate the Gloves in the database
        List<Gloves> glovesList = glovesRepository.findAll();
        assertThat(glovesList).hasSize(databaseSizeBeforeUpdate);
        Gloves testGloves = glovesList.get(glovesList.size() - 1);
        assertThat(testGloves.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGloves.getEffect()).isEqualTo(UPDATED_EFFECT);
        assertThat(testGloves.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testGloves.getDefence()).isEqualTo(UPDATED_DEFENCE);
        assertThat(testGloves.getPart()).isEqualTo(UPDATED_PART);
    }

    @Test
    @Transactional
    public void updateNonExistingGloves() throws Exception {
        int databaseSizeBeforeUpdate = glovesRepository.findAll().size();

        // Create the Gloves
        GlovesDTO glovesDTO = glovesMapper.toDto(gloves);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGlovesMockMvc.perform(put("/api/gloves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(glovesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Gloves in the database
        List<Gloves> glovesList = glovesRepository.findAll();
        assertThat(glovesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGloves() throws Exception {
        // Initialize the database
        glovesRepository.saveAndFlush(gloves);

        int databaseSizeBeforeDelete = glovesRepository.findAll().size();

        // Get the gloves
        restGlovesMockMvc.perform(delete("/api/gloves/{id}", gloves.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Gloves> glovesList = glovesRepository.findAll();
        assertThat(glovesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gloves.class);
        Gloves gloves1 = new Gloves();
        gloves1.setId(1L);
        Gloves gloves2 = new Gloves();
        gloves2.setId(gloves1.getId());
        assertThat(gloves1).isEqualTo(gloves2);
        gloves2.setId(2L);
        assertThat(gloves1).isNotEqualTo(gloves2);
        gloves1.setId(null);
        assertThat(gloves1).isNotEqualTo(gloves2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GlovesDTO.class);
        GlovesDTO glovesDTO1 = new GlovesDTO();
        glovesDTO1.setId(1L);
        GlovesDTO glovesDTO2 = new GlovesDTO();
        assertThat(glovesDTO1).isNotEqualTo(glovesDTO2);
        glovesDTO2.setId(glovesDTO1.getId());
        assertThat(glovesDTO1).isEqualTo(glovesDTO2);
        glovesDTO2.setId(2L);
        assertThat(glovesDTO1).isNotEqualTo(glovesDTO2);
        glovesDTO1.setId(null);
        assertThat(glovesDTO1).isNotEqualTo(glovesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(glovesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(glovesMapper.fromId(null)).isNull();
    }
}

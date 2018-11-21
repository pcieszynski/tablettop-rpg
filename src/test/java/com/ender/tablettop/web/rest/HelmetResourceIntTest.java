package com.ender.tablettop.web.rest;

import com.ender.tablettop.TabletTopRpgApp;

import com.ender.tablettop.domain.Helmet;
import com.ender.tablettop.repository.HelmetRepository;
import com.ender.tablettop.service.HelmetService;
import com.ender.tablettop.service.dto.HelmetDTO;
import com.ender.tablettop.service.mapper.HelmetMapper;
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
 * Test class for the HelmetResource REST controller.
 *
 * @see HelmetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TabletTopRpgApp.class)
public class HelmetResourceIntTest {

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
    private HelmetRepository helmetRepository;

    @Mock
    private HelmetRepository helmetRepositoryMock;

    @Autowired
    private HelmetMapper helmetMapper;

    @Mock
    private HelmetService helmetServiceMock;

    @Autowired
    private HelmetService helmetService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHelmetMockMvc;

    private Helmet helmet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HelmetResource helmetResource = new HelmetResource(helmetService);
        this.restHelmetMockMvc = MockMvcBuilders.standaloneSetup(helmetResource)
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
    public static Helmet createEntity(EntityManager em) {
        Helmet helmet = new Helmet()
            .name(DEFAULT_NAME)
            .effect(DEFAULT_EFFECT)
            .price(DEFAULT_PRICE)
            .defence(DEFAULT_DEFENCE)
            .part(DEFAULT_PART);
        return helmet;
    }

    @Before
    public void initTest() {
        helmet = createEntity(em);
    }

    @Test
    @Transactional
    public void createHelmet() throws Exception {
        int databaseSizeBeforeCreate = helmetRepository.findAll().size();

        // Create the Helmet
        HelmetDTO helmetDTO = helmetMapper.toDto(helmet);
        restHelmetMockMvc.perform(post("/api/helmets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(helmetDTO)))
            .andExpect(status().isCreated());

        // Validate the Helmet in the database
        List<Helmet> helmetList = helmetRepository.findAll();
        assertThat(helmetList).hasSize(databaseSizeBeforeCreate + 1);
        Helmet testHelmet = helmetList.get(helmetList.size() - 1);
        assertThat(testHelmet.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHelmet.getEffect()).isEqualTo(DEFAULT_EFFECT);
        assertThat(testHelmet.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testHelmet.getDefence()).isEqualTo(DEFAULT_DEFENCE);
        assertThat(testHelmet.getPart()).isEqualTo(DEFAULT_PART);
    }

    @Test
    @Transactional
    public void createHelmetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = helmetRepository.findAll().size();

        // Create the Helmet with an existing ID
        helmet.setId(1L);
        HelmetDTO helmetDTO = helmetMapper.toDto(helmet);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHelmetMockMvc.perform(post("/api/helmets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(helmetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Helmet in the database
        List<Helmet> helmetList = helmetRepository.findAll();
        assertThat(helmetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHelmets() throws Exception {
        // Initialize the database
        helmetRepository.saveAndFlush(helmet);

        // Get all the helmetList
        restHelmetMockMvc.perform(get("/api/helmets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(helmet.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].effect").value(hasItem(DEFAULT_EFFECT.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].defence").value(hasItem(DEFAULT_DEFENCE)))
            .andExpect(jsonPath("$.[*].part").value(hasItem(DEFAULT_PART.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllHelmetsWithEagerRelationshipsIsEnabled() throws Exception {
        HelmetResource helmetResource = new HelmetResource(helmetServiceMock);
        when(helmetServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restHelmetMockMvc = MockMvcBuilders.standaloneSetup(helmetResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restHelmetMockMvc.perform(get("/api/helmets?eagerload=true"))
        .andExpect(status().isOk());

        verify(helmetServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllHelmetsWithEagerRelationshipsIsNotEnabled() throws Exception {
        HelmetResource helmetResource = new HelmetResource(helmetServiceMock);
            when(helmetServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restHelmetMockMvc = MockMvcBuilders.standaloneSetup(helmetResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restHelmetMockMvc.perform(get("/api/helmets?eagerload=true"))
        .andExpect(status().isOk());

            verify(helmetServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getHelmet() throws Exception {
        // Initialize the database
        helmetRepository.saveAndFlush(helmet);

        // Get the helmet
        restHelmetMockMvc.perform(get("/api/helmets/{id}", helmet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(helmet.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.effect").value(DEFAULT_EFFECT.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.defence").value(DEFAULT_DEFENCE))
            .andExpect(jsonPath("$.part").value(DEFAULT_PART.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHelmet() throws Exception {
        // Get the helmet
        restHelmetMockMvc.perform(get("/api/helmets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHelmet() throws Exception {
        // Initialize the database
        helmetRepository.saveAndFlush(helmet);

        int databaseSizeBeforeUpdate = helmetRepository.findAll().size();

        // Update the helmet
        Helmet updatedHelmet = helmetRepository.findById(helmet.getId()).get();
        // Disconnect from session so that the updates on updatedHelmet are not directly saved in db
        em.detach(updatedHelmet);
        updatedHelmet
            .name(UPDATED_NAME)
            .effect(UPDATED_EFFECT)
            .price(UPDATED_PRICE)
            .defence(UPDATED_DEFENCE)
            .part(UPDATED_PART);
        HelmetDTO helmetDTO = helmetMapper.toDto(updatedHelmet);

        restHelmetMockMvc.perform(put("/api/helmets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(helmetDTO)))
            .andExpect(status().isOk());

        // Validate the Helmet in the database
        List<Helmet> helmetList = helmetRepository.findAll();
        assertThat(helmetList).hasSize(databaseSizeBeforeUpdate);
        Helmet testHelmet = helmetList.get(helmetList.size() - 1);
        assertThat(testHelmet.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHelmet.getEffect()).isEqualTo(UPDATED_EFFECT);
        assertThat(testHelmet.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testHelmet.getDefence()).isEqualTo(UPDATED_DEFENCE);
        assertThat(testHelmet.getPart()).isEqualTo(UPDATED_PART);
    }

    @Test
    @Transactional
    public void updateNonExistingHelmet() throws Exception {
        int databaseSizeBeforeUpdate = helmetRepository.findAll().size();

        // Create the Helmet
        HelmetDTO helmetDTO = helmetMapper.toDto(helmet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHelmetMockMvc.perform(put("/api/helmets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(helmetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Helmet in the database
        List<Helmet> helmetList = helmetRepository.findAll();
        assertThat(helmetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHelmet() throws Exception {
        // Initialize the database
        helmetRepository.saveAndFlush(helmet);

        int databaseSizeBeforeDelete = helmetRepository.findAll().size();

        // Get the helmet
        restHelmetMockMvc.perform(delete("/api/helmets/{id}", helmet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Helmet> helmetList = helmetRepository.findAll();
        assertThat(helmetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Helmet.class);
        Helmet helmet1 = new Helmet();
        helmet1.setId(1L);
        Helmet helmet2 = new Helmet();
        helmet2.setId(helmet1.getId());
        assertThat(helmet1).isEqualTo(helmet2);
        helmet2.setId(2L);
        assertThat(helmet1).isNotEqualTo(helmet2);
        helmet1.setId(null);
        assertThat(helmet1).isNotEqualTo(helmet2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HelmetDTO.class);
        HelmetDTO helmetDTO1 = new HelmetDTO();
        helmetDTO1.setId(1L);
        HelmetDTO helmetDTO2 = new HelmetDTO();
        assertThat(helmetDTO1).isNotEqualTo(helmetDTO2);
        helmetDTO2.setId(helmetDTO1.getId());
        assertThat(helmetDTO1).isEqualTo(helmetDTO2);
        helmetDTO2.setId(2L);
        assertThat(helmetDTO1).isNotEqualTo(helmetDTO2);
        helmetDTO1.setId(null);
        assertThat(helmetDTO1).isNotEqualTo(helmetDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(helmetMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(helmetMapper.fromId(null)).isNull();
    }
}

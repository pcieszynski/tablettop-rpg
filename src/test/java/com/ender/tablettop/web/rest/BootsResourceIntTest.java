package com.ender.tablettop.web.rest;

import com.ender.tablettop.TabletTopRpgApp;

import com.ender.tablettop.domain.Boots;
import com.ender.tablettop.repository.BootsRepository;
import com.ender.tablettop.service.BootsService;
import com.ender.tablettop.service.dto.BootsDTO;
import com.ender.tablettop.service.mapper.BootsMapper;
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
 * Test class for the BootsResource REST controller.
 *
 * @see BootsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TabletTopRpgApp.class)
public class BootsResourceIntTest {

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
    private BootsRepository bootsRepository;

    @Mock
    private BootsRepository bootsRepositoryMock;

    @Autowired
    private BootsMapper bootsMapper;

    @Mock
    private BootsService bootsServiceMock;

    @Autowired
    private BootsService bootsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBootsMockMvc;

    private Boots boots;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BootsResource bootsResource = new BootsResource(bootsService);
        this.restBootsMockMvc = MockMvcBuilders.standaloneSetup(bootsResource)
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
    public static Boots createEntity(EntityManager em) {
        Boots boots = new Boots()
            .name(DEFAULT_NAME)
            .effect(DEFAULT_EFFECT)
            .price(DEFAULT_PRICE)
            .defence(DEFAULT_DEFENCE)
            .part(DEFAULT_PART);
        return boots;
    }

    @Before
    public void initTest() {
        boots = createEntity(em);
    }

    @Test
    @Transactional
    public void createBoots() throws Exception {
        int databaseSizeBeforeCreate = bootsRepository.findAll().size();

        // Create the Boots
        BootsDTO bootsDTO = bootsMapper.toDto(boots);
        restBootsMockMvc.perform(post("/api/boots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bootsDTO)))
            .andExpect(status().isCreated());

        // Validate the Boots in the database
        List<Boots> bootsList = bootsRepository.findAll();
        assertThat(bootsList).hasSize(databaseSizeBeforeCreate + 1);
        Boots testBoots = bootsList.get(bootsList.size() - 1);
        assertThat(testBoots.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBoots.getEffect()).isEqualTo(DEFAULT_EFFECT);
        assertThat(testBoots.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testBoots.getDefence()).isEqualTo(DEFAULT_DEFENCE);
        assertThat(testBoots.getPart()).isEqualTo(DEFAULT_PART);
    }

    @Test
    @Transactional
    public void createBootsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bootsRepository.findAll().size();

        // Create the Boots with an existing ID
        boots.setId(1L);
        BootsDTO bootsDTO = bootsMapper.toDto(boots);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBootsMockMvc.perform(post("/api/boots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bootsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Boots in the database
        List<Boots> bootsList = bootsRepository.findAll();
        assertThat(bootsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBoots() throws Exception {
        // Initialize the database
        bootsRepository.saveAndFlush(boots);

        // Get all the bootsList
        restBootsMockMvc.perform(get("/api/boots?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(boots.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].effect").value(hasItem(DEFAULT_EFFECT.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].defence").value(hasItem(DEFAULT_DEFENCE)))
            .andExpect(jsonPath("$.[*].part").value(hasItem(DEFAULT_PART.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllBootsWithEagerRelationshipsIsEnabled() throws Exception {
        BootsResource bootsResource = new BootsResource(bootsServiceMock);
        when(bootsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restBootsMockMvc = MockMvcBuilders.standaloneSetup(bootsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restBootsMockMvc.perform(get("/api/boots?eagerload=true"))
        .andExpect(status().isOk());

        verify(bootsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllBootsWithEagerRelationshipsIsNotEnabled() throws Exception {
        BootsResource bootsResource = new BootsResource(bootsServiceMock);
            when(bootsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restBootsMockMvc = MockMvcBuilders.standaloneSetup(bootsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restBootsMockMvc.perform(get("/api/boots?eagerload=true"))
        .andExpect(status().isOk());

            verify(bootsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getBoots() throws Exception {
        // Initialize the database
        bootsRepository.saveAndFlush(boots);

        // Get the boots
        restBootsMockMvc.perform(get("/api/boots/{id}", boots.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(boots.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.effect").value(DEFAULT_EFFECT.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.defence").value(DEFAULT_DEFENCE))
            .andExpect(jsonPath("$.part").value(DEFAULT_PART.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBoots() throws Exception {
        // Get the boots
        restBootsMockMvc.perform(get("/api/boots/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBoots() throws Exception {
        // Initialize the database
        bootsRepository.saveAndFlush(boots);

        int databaseSizeBeforeUpdate = bootsRepository.findAll().size();

        // Update the boots
        Boots updatedBoots = bootsRepository.findById(boots.getId()).get();
        // Disconnect from session so that the updates on updatedBoots are not directly saved in db
        em.detach(updatedBoots);
        updatedBoots
            .name(UPDATED_NAME)
            .effect(UPDATED_EFFECT)
            .price(UPDATED_PRICE)
            .defence(UPDATED_DEFENCE)
            .part(UPDATED_PART);
        BootsDTO bootsDTO = bootsMapper.toDto(updatedBoots);

        restBootsMockMvc.perform(put("/api/boots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bootsDTO)))
            .andExpect(status().isOk());

        // Validate the Boots in the database
        List<Boots> bootsList = bootsRepository.findAll();
        assertThat(bootsList).hasSize(databaseSizeBeforeUpdate);
        Boots testBoots = bootsList.get(bootsList.size() - 1);
        assertThat(testBoots.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBoots.getEffect()).isEqualTo(UPDATED_EFFECT);
        assertThat(testBoots.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testBoots.getDefence()).isEqualTo(UPDATED_DEFENCE);
        assertThat(testBoots.getPart()).isEqualTo(UPDATED_PART);
    }

    @Test
    @Transactional
    public void updateNonExistingBoots() throws Exception {
        int databaseSizeBeforeUpdate = bootsRepository.findAll().size();

        // Create the Boots
        BootsDTO bootsDTO = bootsMapper.toDto(boots);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBootsMockMvc.perform(put("/api/boots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bootsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Boots in the database
        List<Boots> bootsList = bootsRepository.findAll();
        assertThat(bootsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBoots() throws Exception {
        // Initialize the database
        bootsRepository.saveAndFlush(boots);

        int databaseSizeBeforeDelete = bootsRepository.findAll().size();

        // Get the boots
        restBootsMockMvc.perform(delete("/api/boots/{id}", boots.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Boots> bootsList = bootsRepository.findAll();
        assertThat(bootsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Boots.class);
        Boots boots1 = new Boots();
        boots1.setId(1L);
        Boots boots2 = new Boots();
        boots2.setId(boots1.getId());
        assertThat(boots1).isEqualTo(boots2);
        boots2.setId(2L);
        assertThat(boots1).isNotEqualTo(boots2);
        boots1.setId(null);
        assertThat(boots1).isNotEqualTo(boots2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BootsDTO.class);
        BootsDTO bootsDTO1 = new BootsDTO();
        bootsDTO1.setId(1L);
        BootsDTO bootsDTO2 = new BootsDTO();
        assertThat(bootsDTO1).isNotEqualTo(bootsDTO2);
        bootsDTO2.setId(bootsDTO1.getId());
        assertThat(bootsDTO1).isEqualTo(bootsDTO2);
        bootsDTO2.setId(2L);
        assertThat(bootsDTO1).isNotEqualTo(bootsDTO2);
        bootsDTO1.setId(null);
        assertThat(bootsDTO1).isNotEqualTo(bootsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(bootsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(bootsMapper.fromId(null)).isNull();
    }
}

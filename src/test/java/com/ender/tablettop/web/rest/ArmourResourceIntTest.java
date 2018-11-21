package com.ender.tablettop.web.rest;

import com.ender.tablettop.TabletTopRpgApp;

import com.ender.tablettop.domain.Armour;
import com.ender.tablettop.repository.ArmourRepository;
import com.ender.tablettop.service.ArmourService;
import com.ender.tablettop.service.dto.ArmourDTO;
import com.ender.tablettop.service.mapper.ArmourMapper;
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
 * Test class for the ArmourResource REST controller.
 *
 * @see ArmourResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TabletTopRpgApp.class)
public class ArmourResourceIntTest {

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
    private ArmourRepository armourRepository;

    @Mock
    private ArmourRepository armourRepositoryMock;

    @Autowired
    private ArmourMapper armourMapper;

    @Mock
    private ArmourService armourServiceMock;

    @Autowired
    private ArmourService armourService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restArmourMockMvc;

    private Armour armour;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ArmourResource armourResource = new ArmourResource(armourService);
        this.restArmourMockMvc = MockMvcBuilders.standaloneSetup(armourResource)
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
    public static Armour createEntity(EntityManager em) {
        Armour armour = new Armour()
            .name(DEFAULT_NAME)
            .effect(DEFAULT_EFFECT)
            .price(DEFAULT_PRICE)
            .defence(DEFAULT_DEFENCE)
            .part(DEFAULT_PART);
        return armour;
    }

    @Before
    public void initTest() {
        armour = createEntity(em);
    }

    @Test
    @Transactional
    public void createArmour() throws Exception {
        int databaseSizeBeforeCreate = armourRepository.findAll().size();

        // Create the Armour
        ArmourDTO armourDTO = armourMapper.toDto(armour);
        restArmourMockMvc.perform(post("/api/armours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(armourDTO)))
            .andExpect(status().isCreated());

        // Validate the Armour in the database
        List<Armour> armourList = armourRepository.findAll();
        assertThat(armourList).hasSize(databaseSizeBeforeCreate + 1);
        Armour testArmour = armourList.get(armourList.size() - 1);
        assertThat(testArmour.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testArmour.getEffect()).isEqualTo(DEFAULT_EFFECT);
        assertThat(testArmour.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testArmour.getDefence()).isEqualTo(DEFAULT_DEFENCE);
        assertThat(testArmour.getPart()).isEqualTo(DEFAULT_PART);
    }

    @Test
    @Transactional
    public void createArmourWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = armourRepository.findAll().size();

        // Create the Armour with an existing ID
        armour.setId(1L);
        ArmourDTO armourDTO = armourMapper.toDto(armour);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArmourMockMvc.perform(post("/api/armours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(armourDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Armour in the database
        List<Armour> armourList = armourRepository.findAll();
        assertThat(armourList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllArmours() throws Exception {
        // Initialize the database
        armourRepository.saveAndFlush(armour);

        // Get all the armourList
        restArmourMockMvc.perform(get("/api/armours?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(armour.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].effect").value(hasItem(DEFAULT_EFFECT.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].defence").value(hasItem(DEFAULT_DEFENCE)))
            .andExpect(jsonPath("$.[*].part").value(hasItem(DEFAULT_PART.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllArmoursWithEagerRelationshipsIsEnabled() throws Exception {
        ArmourResource armourResource = new ArmourResource(armourServiceMock);
        when(armourServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restArmourMockMvc = MockMvcBuilders.standaloneSetup(armourResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restArmourMockMvc.perform(get("/api/armours?eagerload=true"))
        .andExpect(status().isOk());

        verify(armourServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllArmoursWithEagerRelationshipsIsNotEnabled() throws Exception {
        ArmourResource armourResource = new ArmourResource(armourServiceMock);
            when(armourServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restArmourMockMvc = MockMvcBuilders.standaloneSetup(armourResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restArmourMockMvc.perform(get("/api/armours?eagerload=true"))
        .andExpect(status().isOk());

            verify(armourServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getArmour() throws Exception {
        // Initialize the database
        armourRepository.saveAndFlush(armour);

        // Get the armour
        restArmourMockMvc.perform(get("/api/armours/{id}", armour.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(armour.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.effect").value(DEFAULT_EFFECT.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.defence").value(DEFAULT_DEFENCE))
            .andExpect(jsonPath("$.part").value(DEFAULT_PART.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingArmour() throws Exception {
        // Get the armour
        restArmourMockMvc.perform(get("/api/armours/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArmour() throws Exception {
        // Initialize the database
        armourRepository.saveAndFlush(armour);

        int databaseSizeBeforeUpdate = armourRepository.findAll().size();

        // Update the armour
        Armour updatedArmour = armourRepository.findById(armour.getId()).get();
        // Disconnect from session so that the updates on updatedArmour are not directly saved in db
        em.detach(updatedArmour);
        updatedArmour
            .name(UPDATED_NAME)
            .effect(UPDATED_EFFECT)
            .price(UPDATED_PRICE)
            .defence(UPDATED_DEFENCE)
            .part(UPDATED_PART);
        ArmourDTO armourDTO = armourMapper.toDto(updatedArmour);

        restArmourMockMvc.perform(put("/api/armours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(armourDTO)))
            .andExpect(status().isOk());

        // Validate the Armour in the database
        List<Armour> armourList = armourRepository.findAll();
        assertThat(armourList).hasSize(databaseSizeBeforeUpdate);
        Armour testArmour = armourList.get(armourList.size() - 1);
        assertThat(testArmour.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testArmour.getEffect()).isEqualTo(UPDATED_EFFECT);
        assertThat(testArmour.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testArmour.getDefence()).isEqualTo(UPDATED_DEFENCE);
        assertThat(testArmour.getPart()).isEqualTo(UPDATED_PART);
    }

    @Test
    @Transactional
    public void updateNonExistingArmour() throws Exception {
        int databaseSizeBeforeUpdate = armourRepository.findAll().size();

        // Create the Armour
        ArmourDTO armourDTO = armourMapper.toDto(armour);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArmourMockMvc.perform(put("/api/armours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(armourDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Armour in the database
        List<Armour> armourList = armourRepository.findAll();
        assertThat(armourList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteArmour() throws Exception {
        // Initialize the database
        armourRepository.saveAndFlush(armour);

        int databaseSizeBeforeDelete = armourRepository.findAll().size();

        // Get the armour
        restArmourMockMvc.perform(delete("/api/armours/{id}", armour.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Armour> armourList = armourRepository.findAll();
        assertThat(armourList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Armour.class);
        Armour armour1 = new Armour();
        armour1.setId(1L);
        Armour armour2 = new Armour();
        armour2.setId(armour1.getId());
        assertThat(armour1).isEqualTo(armour2);
        armour2.setId(2L);
        assertThat(armour1).isNotEqualTo(armour2);
        armour1.setId(null);
        assertThat(armour1).isNotEqualTo(armour2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArmourDTO.class);
        ArmourDTO armourDTO1 = new ArmourDTO();
        armourDTO1.setId(1L);
        ArmourDTO armourDTO2 = new ArmourDTO();
        assertThat(armourDTO1).isNotEqualTo(armourDTO2);
        armourDTO2.setId(armourDTO1.getId());
        assertThat(armourDTO1).isEqualTo(armourDTO2);
        armourDTO2.setId(2L);
        assertThat(armourDTO1).isNotEqualTo(armourDTO2);
        armourDTO1.setId(null);
        assertThat(armourDTO1).isNotEqualTo(armourDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(armourMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(armourMapper.fromId(null)).isNull();
    }
}

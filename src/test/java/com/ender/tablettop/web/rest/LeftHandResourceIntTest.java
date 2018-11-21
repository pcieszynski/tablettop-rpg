package com.ender.tablettop.web.rest;

import com.ender.tablettop.TabletTopRpgApp;

import com.ender.tablettop.domain.LeftHand;
import com.ender.tablettop.repository.LeftHandRepository;
import com.ender.tablettop.service.LeftHandService;
import com.ender.tablettop.service.dto.LeftHandDTO;
import com.ender.tablettop.service.mapper.LeftHandMapper;
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
 * Test class for the LeftHandResource REST controller.
 *
 * @see LeftHandResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TabletTopRpgApp.class)
public class LeftHandResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EFFECT = "AAAAAAAAAA";
    private static final String UPDATED_EFFECT = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRICE = 1;
    private static final Integer UPDATED_PRICE = 2;

    private static final String DEFAULT_ATTACK = "AAAAAAAAAA";
    private static final String UPDATED_ATTACK = "BBBBBBBBBB";

    private static final Integer DEFAULT_DEFENSE = 1;
    private static final Integer UPDATED_DEFENSE = 2;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private LeftHandRepository leftHandRepository;

    @Mock
    private LeftHandRepository leftHandRepositoryMock;

    @Autowired
    private LeftHandMapper leftHandMapper;

    @Mock
    private LeftHandService leftHandServiceMock;

    @Autowired
    private LeftHandService leftHandService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLeftHandMockMvc;

    private LeftHand leftHand;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LeftHandResource leftHandResource = new LeftHandResource(leftHandService);
        this.restLeftHandMockMvc = MockMvcBuilders.standaloneSetup(leftHandResource)
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
    public static LeftHand createEntity(EntityManager em) {
        LeftHand leftHand = new LeftHand()
            .name(DEFAULT_NAME)
            .effect(DEFAULT_EFFECT)
            .price(DEFAULT_PRICE)
            .attack(DEFAULT_ATTACK)
            .defense(DEFAULT_DEFENSE)
            .type(DEFAULT_TYPE);
        return leftHand;
    }

    @Before
    public void initTest() {
        leftHand = createEntity(em);
    }

    @Test
    @Transactional
    public void createLeftHand() throws Exception {
        int databaseSizeBeforeCreate = leftHandRepository.findAll().size();

        // Create the LeftHand
        LeftHandDTO leftHandDTO = leftHandMapper.toDto(leftHand);
        restLeftHandMockMvc.perform(post("/api/left-hands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leftHandDTO)))
            .andExpect(status().isCreated());

        // Validate the LeftHand in the database
        List<LeftHand> leftHandList = leftHandRepository.findAll();
        assertThat(leftHandList).hasSize(databaseSizeBeforeCreate + 1);
        LeftHand testLeftHand = leftHandList.get(leftHandList.size() - 1);
        assertThat(testLeftHand.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLeftHand.getEffect()).isEqualTo(DEFAULT_EFFECT);
        assertThat(testLeftHand.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testLeftHand.getAttack()).isEqualTo(DEFAULT_ATTACK);
        assertThat(testLeftHand.getDefense()).isEqualTo(DEFAULT_DEFENSE);
        assertThat(testLeftHand.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createLeftHandWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = leftHandRepository.findAll().size();

        // Create the LeftHand with an existing ID
        leftHand.setId(1L);
        LeftHandDTO leftHandDTO = leftHandMapper.toDto(leftHand);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeftHandMockMvc.perform(post("/api/left-hands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leftHandDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LeftHand in the database
        List<LeftHand> leftHandList = leftHandRepository.findAll();
        assertThat(leftHandList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLeftHands() throws Exception {
        // Initialize the database
        leftHandRepository.saveAndFlush(leftHand);

        // Get all the leftHandList
        restLeftHandMockMvc.perform(get("/api/left-hands?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leftHand.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].effect").value(hasItem(DEFAULT_EFFECT.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].attack").value(hasItem(DEFAULT_ATTACK.toString())))
            .andExpect(jsonPath("$.[*].defense").value(hasItem(DEFAULT_DEFENSE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllLeftHandsWithEagerRelationshipsIsEnabled() throws Exception {
        LeftHandResource leftHandResource = new LeftHandResource(leftHandServiceMock);
        when(leftHandServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restLeftHandMockMvc = MockMvcBuilders.standaloneSetup(leftHandResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restLeftHandMockMvc.perform(get("/api/left-hands?eagerload=true"))
        .andExpect(status().isOk());

        verify(leftHandServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllLeftHandsWithEagerRelationshipsIsNotEnabled() throws Exception {
        LeftHandResource leftHandResource = new LeftHandResource(leftHandServiceMock);
            when(leftHandServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restLeftHandMockMvc = MockMvcBuilders.standaloneSetup(leftHandResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restLeftHandMockMvc.perform(get("/api/left-hands?eagerload=true"))
        .andExpect(status().isOk());

            verify(leftHandServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getLeftHand() throws Exception {
        // Initialize the database
        leftHandRepository.saveAndFlush(leftHand);

        // Get the leftHand
        restLeftHandMockMvc.perform(get("/api/left-hands/{id}", leftHand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(leftHand.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.effect").value(DEFAULT_EFFECT.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.attack").value(DEFAULT_ATTACK.toString()))
            .andExpect(jsonPath("$.defense").value(DEFAULT_DEFENSE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLeftHand() throws Exception {
        // Get the leftHand
        restLeftHandMockMvc.perform(get("/api/left-hands/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLeftHand() throws Exception {
        // Initialize the database
        leftHandRepository.saveAndFlush(leftHand);

        int databaseSizeBeforeUpdate = leftHandRepository.findAll().size();

        // Update the leftHand
        LeftHand updatedLeftHand = leftHandRepository.findById(leftHand.getId()).get();
        // Disconnect from session so that the updates on updatedLeftHand are not directly saved in db
        em.detach(updatedLeftHand);
        updatedLeftHand
            .name(UPDATED_NAME)
            .effect(UPDATED_EFFECT)
            .price(UPDATED_PRICE)
            .attack(UPDATED_ATTACK)
            .defense(UPDATED_DEFENSE)
            .type(UPDATED_TYPE);
        LeftHandDTO leftHandDTO = leftHandMapper.toDto(updatedLeftHand);

        restLeftHandMockMvc.perform(put("/api/left-hands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leftHandDTO)))
            .andExpect(status().isOk());

        // Validate the LeftHand in the database
        List<LeftHand> leftHandList = leftHandRepository.findAll();
        assertThat(leftHandList).hasSize(databaseSizeBeforeUpdate);
        LeftHand testLeftHand = leftHandList.get(leftHandList.size() - 1);
        assertThat(testLeftHand.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLeftHand.getEffect()).isEqualTo(UPDATED_EFFECT);
        assertThat(testLeftHand.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testLeftHand.getAttack()).isEqualTo(UPDATED_ATTACK);
        assertThat(testLeftHand.getDefense()).isEqualTo(UPDATED_DEFENSE);
        assertThat(testLeftHand.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingLeftHand() throws Exception {
        int databaseSizeBeforeUpdate = leftHandRepository.findAll().size();

        // Create the LeftHand
        LeftHandDTO leftHandDTO = leftHandMapper.toDto(leftHand);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeftHandMockMvc.perform(put("/api/left-hands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leftHandDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LeftHand in the database
        List<LeftHand> leftHandList = leftHandRepository.findAll();
        assertThat(leftHandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLeftHand() throws Exception {
        // Initialize the database
        leftHandRepository.saveAndFlush(leftHand);

        int databaseSizeBeforeDelete = leftHandRepository.findAll().size();

        // Get the leftHand
        restLeftHandMockMvc.perform(delete("/api/left-hands/{id}", leftHand.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LeftHand> leftHandList = leftHandRepository.findAll();
        assertThat(leftHandList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeftHand.class);
        LeftHand leftHand1 = new LeftHand();
        leftHand1.setId(1L);
        LeftHand leftHand2 = new LeftHand();
        leftHand2.setId(leftHand1.getId());
        assertThat(leftHand1).isEqualTo(leftHand2);
        leftHand2.setId(2L);
        assertThat(leftHand1).isNotEqualTo(leftHand2);
        leftHand1.setId(null);
        assertThat(leftHand1).isNotEqualTo(leftHand2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeftHandDTO.class);
        LeftHandDTO leftHandDTO1 = new LeftHandDTO();
        leftHandDTO1.setId(1L);
        LeftHandDTO leftHandDTO2 = new LeftHandDTO();
        assertThat(leftHandDTO1).isNotEqualTo(leftHandDTO2);
        leftHandDTO2.setId(leftHandDTO1.getId());
        assertThat(leftHandDTO1).isEqualTo(leftHandDTO2);
        leftHandDTO2.setId(2L);
        assertThat(leftHandDTO1).isNotEqualTo(leftHandDTO2);
        leftHandDTO1.setId(null);
        assertThat(leftHandDTO1).isNotEqualTo(leftHandDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(leftHandMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(leftHandMapper.fromId(null)).isNull();
    }
}

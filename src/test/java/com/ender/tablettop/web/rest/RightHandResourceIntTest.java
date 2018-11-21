package com.ender.tablettop.web.rest;

import com.ender.tablettop.TabletTopRpgApp;

import com.ender.tablettop.domain.RightHand;
import com.ender.tablettop.repository.RightHandRepository;
import com.ender.tablettop.service.RightHandService;
import com.ender.tablettop.service.dto.RightHandDTO;
import com.ender.tablettop.service.mapper.RightHandMapper;
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
 * Test class for the RightHandResource REST controller.
 *
 * @see RightHandResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TabletTopRpgApp.class)
public class RightHandResourceIntTest {

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
    private RightHandRepository rightHandRepository;

    @Mock
    private RightHandRepository rightHandRepositoryMock;

    @Autowired
    private RightHandMapper rightHandMapper;

    @Mock
    private RightHandService rightHandServiceMock;

    @Autowired
    private RightHandService rightHandService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRightHandMockMvc;

    private RightHand rightHand;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RightHandResource rightHandResource = new RightHandResource(rightHandService);
        this.restRightHandMockMvc = MockMvcBuilders.standaloneSetup(rightHandResource)
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
    public static RightHand createEntity(EntityManager em) {
        RightHand rightHand = new RightHand()
            .name(DEFAULT_NAME)
            .effect(DEFAULT_EFFECT)
            .price(DEFAULT_PRICE)
            .attack(DEFAULT_ATTACK)
            .defense(DEFAULT_DEFENSE)
            .type(DEFAULT_TYPE);
        return rightHand;
    }

    @Before
    public void initTest() {
        rightHand = createEntity(em);
    }

    @Test
    @Transactional
    public void createRightHand() throws Exception {
        int databaseSizeBeforeCreate = rightHandRepository.findAll().size();

        // Create the RightHand
        RightHandDTO rightHandDTO = rightHandMapper.toDto(rightHand);
        restRightHandMockMvc.perform(post("/api/right-hands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rightHandDTO)))
            .andExpect(status().isCreated());

        // Validate the RightHand in the database
        List<RightHand> rightHandList = rightHandRepository.findAll();
        assertThat(rightHandList).hasSize(databaseSizeBeforeCreate + 1);
        RightHand testRightHand = rightHandList.get(rightHandList.size() - 1);
        assertThat(testRightHand.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRightHand.getEffect()).isEqualTo(DEFAULT_EFFECT);
        assertThat(testRightHand.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testRightHand.getAttack()).isEqualTo(DEFAULT_ATTACK);
        assertThat(testRightHand.getDefense()).isEqualTo(DEFAULT_DEFENSE);
        assertThat(testRightHand.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createRightHandWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rightHandRepository.findAll().size();

        // Create the RightHand with an existing ID
        rightHand.setId(1L);
        RightHandDTO rightHandDTO = rightHandMapper.toDto(rightHand);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRightHandMockMvc.perform(post("/api/right-hands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rightHandDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RightHand in the database
        List<RightHand> rightHandList = rightHandRepository.findAll();
        assertThat(rightHandList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRightHands() throws Exception {
        // Initialize the database
        rightHandRepository.saveAndFlush(rightHand);

        // Get all the rightHandList
        restRightHandMockMvc.perform(get("/api/right-hands?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rightHand.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].effect").value(hasItem(DEFAULT_EFFECT.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].attack").value(hasItem(DEFAULT_ATTACK.toString())))
            .andExpect(jsonPath("$.[*].defense").value(hasItem(DEFAULT_DEFENSE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllRightHandsWithEagerRelationshipsIsEnabled() throws Exception {
        RightHandResource rightHandResource = new RightHandResource(rightHandServiceMock);
        when(rightHandServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restRightHandMockMvc = MockMvcBuilders.standaloneSetup(rightHandResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restRightHandMockMvc.perform(get("/api/right-hands?eagerload=true"))
        .andExpect(status().isOk());

        verify(rightHandServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllRightHandsWithEagerRelationshipsIsNotEnabled() throws Exception {
        RightHandResource rightHandResource = new RightHandResource(rightHandServiceMock);
            when(rightHandServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restRightHandMockMvc = MockMvcBuilders.standaloneSetup(rightHandResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restRightHandMockMvc.perform(get("/api/right-hands?eagerload=true"))
        .andExpect(status().isOk());

            verify(rightHandServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getRightHand() throws Exception {
        // Initialize the database
        rightHandRepository.saveAndFlush(rightHand);

        // Get the rightHand
        restRightHandMockMvc.perform(get("/api/right-hands/{id}", rightHand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rightHand.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.effect").value(DEFAULT_EFFECT.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.attack").value(DEFAULT_ATTACK.toString()))
            .andExpect(jsonPath("$.defense").value(DEFAULT_DEFENSE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRightHand() throws Exception {
        // Get the rightHand
        restRightHandMockMvc.perform(get("/api/right-hands/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRightHand() throws Exception {
        // Initialize the database
        rightHandRepository.saveAndFlush(rightHand);

        int databaseSizeBeforeUpdate = rightHandRepository.findAll().size();

        // Update the rightHand
        RightHand updatedRightHand = rightHandRepository.findById(rightHand.getId()).get();
        // Disconnect from session so that the updates on updatedRightHand are not directly saved in db
        em.detach(updatedRightHand);
        updatedRightHand
            .name(UPDATED_NAME)
            .effect(UPDATED_EFFECT)
            .price(UPDATED_PRICE)
            .attack(UPDATED_ATTACK)
            .defense(UPDATED_DEFENSE)
            .type(UPDATED_TYPE);
        RightHandDTO rightHandDTO = rightHandMapper.toDto(updatedRightHand);

        restRightHandMockMvc.perform(put("/api/right-hands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rightHandDTO)))
            .andExpect(status().isOk());

        // Validate the RightHand in the database
        List<RightHand> rightHandList = rightHandRepository.findAll();
        assertThat(rightHandList).hasSize(databaseSizeBeforeUpdate);
        RightHand testRightHand = rightHandList.get(rightHandList.size() - 1);
        assertThat(testRightHand.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRightHand.getEffect()).isEqualTo(UPDATED_EFFECT);
        assertThat(testRightHand.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testRightHand.getAttack()).isEqualTo(UPDATED_ATTACK);
        assertThat(testRightHand.getDefense()).isEqualTo(UPDATED_DEFENSE);
        assertThat(testRightHand.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingRightHand() throws Exception {
        int databaseSizeBeforeUpdate = rightHandRepository.findAll().size();

        // Create the RightHand
        RightHandDTO rightHandDTO = rightHandMapper.toDto(rightHand);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRightHandMockMvc.perform(put("/api/right-hands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rightHandDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RightHand in the database
        List<RightHand> rightHandList = rightHandRepository.findAll();
        assertThat(rightHandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRightHand() throws Exception {
        // Initialize the database
        rightHandRepository.saveAndFlush(rightHand);

        int databaseSizeBeforeDelete = rightHandRepository.findAll().size();

        // Get the rightHand
        restRightHandMockMvc.perform(delete("/api/right-hands/{id}", rightHand.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RightHand> rightHandList = rightHandRepository.findAll();
        assertThat(rightHandList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RightHand.class);
        RightHand rightHand1 = new RightHand();
        rightHand1.setId(1L);
        RightHand rightHand2 = new RightHand();
        rightHand2.setId(rightHand1.getId());
        assertThat(rightHand1).isEqualTo(rightHand2);
        rightHand2.setId(2L);
        assertThat(rightHand1).isNotEqualTo(rightHand2);
        rightHand1.setId(null);
        assertThat(rightHand1).isNotEqualTo(rightHand2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RightHandDTO.class);
        RightHandDTO rightHandDTO1 = new RightHandDTO();
        rightHandDTO1.setId(1L);
        RightHandDTO rightHandDTO2 = new RightHandDTO();
        assertThat(rightHandDTO1).isNotEqualTo(rightHandDTO2);
        rightHandDTO2.setId(rightHandDTO1.getId());
        assertThat(rightHandDTO1).isEqualTo(rightHandDTO2);
        rightHandDTO2.setId(2L);
        assertThat(rightHandDTO1).isNotEqualTo(rightHandDTO2);
        rightHandDTO1.setId(null);
        assertThat(rightHandDTO1).isNotEqualTo(rightHandDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(rightHandMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(rightHandMapper.fromId(null)).isNull();
    }
}

package com.lxisoft.redalert.web.rest;

import com.lxisoft.redalert.RedalertApp;

import com.lxisoft.redalert.domain.EmergencyService;
import com.lxisoft.redalert.repository.EmergencyServiceRepository;
import com.lxisoft.redalert.service.EmergencyServiceService;
import com.lxisoft.redalert.service.dto.EmergencyServiceDTO;
import com.lxisoft.redalert.service.mapper.EmergencyServiceMapper;
import com.lxisoft.redalert.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.lxisoft.redalert.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lxisoft.redalert.domain.enumeration.ServiceName;
/**
 * Test class for the EmergencyServiceResource REST controller.
 *
 * @see EmergencyServiceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedalertApp.class)
public class EmergencyServiceResourceIntTest {

    private static final ServiceName DEFAULT_NAME = ServiceName.FIRE;
    private static final ServiceName UPDATED_NAME = ServiceName.AMBULANCE;

    private static final Long DEFAULT_PHONE = 1L;
    private static final Long UPDATED_PHONE = 2L;

    @Autowired
    private EmergencyServiceRepository emergencyServiceRepository;

    @Autowired
    private EmergencyServiceMapper emergencyServiceMapper;

    @Autowired
    private EmergencyServiceService emergencyServiceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restEmergencyServiceMockMvc;

    private EmergencyService emergencyService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmergencyServiceResource emergencyServiceResource = new EmergencyServiceResource(emergencyServiceService);
        this.restEmergencyServiceMockMvc = MockMvcBuilders.standaloneSetup(emergencyServiceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmergencyService createEntity(EntityManager em) {
        EmergencyService emergencyService = new EmergencyService()
            .name(DEFAULT_NAME)
            .phone(DEFAULT_PHONE);
        return emergencyService;
    }

    @Before
    public void initTest() {
        emergencyService = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmergencyService() throws Exception {
        int databaseSizeBeforeCreate = emergencyServiceRepository.findAll().size();

        // Create the EmergencyService
        EmergencyServiceDTO emergencyServiceDTO = emergencyServiceMapper.toDto(emergencyService);
        restEmergencyServiceMockMvc.perform(post("/api/emergency-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emergencyServiceDTO)))
            .andExpect(status().isCreated());

        // Validate the EmergencyService in the database
        List<EmergencyService> emergencyServiceList = emergencyServiceRepository.findAll();
        assertThat(emergencyServiceList).hasSize(databaseSizeBeforeCreate + 1);
        EmergencyService testEmergencyService = emergencyServiceList.get(emergencyServiceList.size() - 1);
        assertThat(testEmergencyService.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEmergencyService.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    public void createEmergencyServiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emergencyServiceRepository.findAll().size();

        // Create the EmergencyService with an existing ID
        emergencyService.setId(1L);
        EmergencyServiceDTO emergencyServiceDTO = emergencyServiceMapper.toDto(emergencyService);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmergencyServiceMockMvc.perform(post("/api/emergency-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emergencyServiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmergencyService in the database
        List<EmergencyService> emergencyServiceList = emergencyServiceRepository.findAll();
        assertThat(emergencyServiceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEmergencyServices() throws Exception {
        // Initialize the database
        emergencyServiceRepository.saveAndFlush(emergencyService);

        // Get all the emergencyServiceList
        restEmergencyServiceMockMvc.perform(get("/api/emergency-services?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emergencyService.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.intValue())));
    }
    
    @Test
    @Transactional
    public void getEmergencyService() throws Exception {
        // Initialize the database
        emergencyServiceRepository.saveAndFlush(emergencyService);

        // Get the emergencyService
        restEmergencyServiceMockMvc.perform(get("/api/emergency-services/{id}", emergencyService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(emergencyService.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEmergencyService() throws Exception {
        // Get the emergencyService
        restEmergencyServiceMockMvc.perform(get("/api/emergency-services/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmergencyService() throws Exception {
        // Initialize the database
        emergencyServiceRepository.saveAndFlush(emergencyService);

        int databaseSizeBeforeUpdate = emergencyServiceRepository.findAll().size();

        // Update the emergencyService
        EmergencyService updatedEmergencyService = emergencyServiceRepository.findById(emergencyService.getId()).get();
        // Disconnect from session so that the updates on updatedEmergencyService are not directly saved in db
        em.detach(updatedEmergencyService);
        updatedEmergencyService
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE);
        EmergencyServiceDTO emergencyServiceDTO = emergencyServiceMapper.toDto(updatedEmergencyService);

        restEmergencyServiceMockMvc.perform(put("/api/emergency-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emergencyServiceDTO)))
            .andExpect(status().isOk());

        // Validate the EmergencyService in the database
        List<EmergencyService> emergencyServiceList = emergencyServiceRepository.findAll();
        assertThat(emergencyServiceList).hasSize(databaseSizeBeforeUpdate);
        EmergencyService testEmergencyService = emergencyServiceList.get(emergencyServiceList.size() - 1);
        assertThat(testEmergencyService.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmergencyService.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingEmergencyService() throws Exception {
        int databaseSizeBeforeUpdate = emergencyServiceRepository.findAll().size();

        // Create the EmergencyService
        EmergencyServiceDTO emergencyServiceDTO = emergencyServiceMapper.toDto(emergencyService);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmergencyServiceMockMvc.perform(put("/api/emergency-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emergencyServiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmergencyService in the database
        List<EmergencyService> emergencyServiceList = emergencyServiceRepository.findAll();
        assertThat(emergencyServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmergencyService() throws Exception {
        // Initialize the database
        emergencyServiceRepository.saveAndFlush(emergencyService);

        int databaseSizeBeforeDelete = emergencyServiceRepository.findAll().size();

        // Delete the emergencyService
        restEmergencyServiceMockMvc.perform(delete("/api/emergency-services/{id}", emergencyService.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EmergencyService> emergencyServiceList = emergencyServiceRepository.findAll();
        assertThat(emergencyServiceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmergencyService.class);
        EmergencyService emergencyService1 = new EmergencyService();
        emergencyService1.setId(1L);
        EmergencyService emergencyService2 = new EmergencyService();
        emergencyService2.setId(emergencyService1.getId());
        assertThat(emergencyService1).isEqualTo(emergencyService2);
        emergencyService2.setId(2L);
        assertThat(emergencyService1).isNotEqualTo(emergencyService2);
        emergencyService1.setId(null);
        assertThat(emergencyService1).isNotEqualTo(emergencyService2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmergencyServiceDTO.class);
        EmergencyServiceDTO emergencyServiceDTO1 = new EmergencyServiceDTO();
        emergencyServiceDTO1.setId(1L);
        EmergencyServiceDTO emergencyServiceDTO2 = new EmergencyServiceDTO();
        assertThat(emergencyServiceDTO1).isNotEqualTo(emergencyServiceDTO2);
        emergencyServiceDTO2.setId(emergencyServiceDTO1.getId());
        assertThat(emergencyServiceDTO1).isEqualTo(emergencyServiceDTO2);
        emergencyServiceDTO2.setId(2L);
        assertThat(emergencyServiceDTO1).isNotEqualTo(emergencyServiceDTO2);
        emergencyServiceDTO1.setId(null);
        assertThat(emergencyServiceDTO1).isNotEqualTo(emergencyServiceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(emergencyServiceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(emergencyServiceMapper.fromId(null)).isNull();
    }
}

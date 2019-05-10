package com.lxisoft.redalert.web.rest;

import com.lxisoft.redalert.RedalertApp;

import com.lxisoft.redalert.domain.ServiceAuthority;
import com.lxisoft.redalert.repository.ServiceAuthorityRepository;
import com.lxisoft.redalert.service.ServiceAuthorityService;
import com.lxisoft.redalert.service.dto.ServiceAuthorityDTO;
import com.lxisoft.redalert.service.mapper.ServiceAuthorityMapper;
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

/**
 * Test class for the ServiceAuthorityResource REST controller.
 *
 * @see ServiceAuthorityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedalertApp.class)
public class ServiceAuthorityResourceIntTest {

    private static final String DEFAULT_DISTRICT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISTRICT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHORITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_AUTHORITY_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_PHONE = 1L;
    private static final Long UPDATED_PHONE = 2L;

    @Autowired
    private ServiceAuthorityRepository serviceAuthorityRepository;

    @Autowired
    private ServiceAuthorityMapper serviceAuthorityMapper;

    @Autowired
    private ServiceAuthorityService serviceAuthorityService;

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

    private MockMvc restServiceAuthorityMockMvc;

    private ServiceAuthority serviceAuthority;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceAuthorityResource serviceAuthorityResource = new ServiceAuthorityResource(serviceAuthorityService);
        this.restServiceAuthorityMockMvc = MockMvcBuilders.standaloneSetup(serviceAuthorityResource)
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
    public static ServiceAuthority createEntity(EntityManager em) {
        ServiceAuthority serviceAuthority = new ServiceAuthority()
            .districtName(DEFAULT_DISTRICT_NAME)
            .authorityName(DEFAULT_AUTHORITY_NAME)
            .phone(DEFAULT_PHONE);
        return serviceAuthority;
    }

    @Before
    public void initTest() {
        serviceAuthority = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceAuthority() throws Exception {
        int databaseSizeBeforeCreate = serviceAuthorityRepository.findAll().size();

        // Create the ServiceAuthority
        ServiceAuthorityDTO serviceAuthorityDTO = serviceAuthorityMapper.toDto(serviceAuthority);
        restServiceAuthorityMockMvc.perform(post("/api/service-authorities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceAuthorityDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceAuthority in the database
        List<ServiceAuthority> serviceAuthorityList = serviceAuthorityRepository.findAll();
        assertThat(serviceAuthorityList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceAuthority testServiceAuthority = serviceAuthorityList.get(serviceAuthorityList.size() - 1);
        assertThat(testServiceAuthority.getDistrictName()).isEqualTo(DEFAULT_DISTRICT_NAME);
        assertThat(testServiceAuthority.getAuthorityName()).isEqualTo(DEFAULT_AUTHORITY_NAME);
        assertThat(testServiceAuthority.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    public void createServiceAuthorityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceAuthorityRepository.findAll().size();

        // Create the ServiceAuthority with an existing ID
        serviceAuthority.setId(1L);
        ServiceAuthorityDTO serviceAuthorityDTO = serviceAuthorityMapper.toDto(serviceAuthority);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceAuthorityMockMvc.perform(post("/api/service-authorities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceAuthorityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceAuthority in the database
        List<ServiceAuthority> serviceAuthorityList = serviceAuthorityRepository.findAll();
        assertThat(serviceAuthorityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllServiceAuthorities() throws Exception {
        // Initialize the database
        serviceAuthorityRepository.saveAndFlush(serviceAuthority);

        // Get all the serviceAuthorityList
        restServiceAuthorityMockMvc.perform(get("/api/service-authorities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceAuthority.getId().intValue())))
            .andExpect(jsonPath("$.[*].districtName").value(hasItem(DEFAULT_DISTRICT_NAME.toString())))
            .andExpect(jsonPath("$.[*].authorityName").value(hasItem(DEFAULT_AUTHORITY_NAME.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.intValue())));
    }
    
    @Test
    @Transactional
    public void getServiceAuthority() throws Exception {
        // Initialize the database
        serviceAuthorityRepository.saveAndFlush(serviceAuthority);

        // Get the serviceAuthority
        restServiceAuthorityMockMvc.perform(get("/api/service-authorities/{id}", serviceAuthority.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceAuthority.getId().intValue()))
            .andExpect(jsonPath("$.districtName").value(DEFAULT_DISTRICT_NAME.toString()))
            .andExpect(jsonPath("$.authorityName").value(DEFAULT_AUTHORITY_NAME.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingServiceAuthority() throws Exception {
        // Get the serviceAuthority
        restServiceAuthorityMockMvc.perform(get("/api/service-authorities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceAuthority() throws Exception {
        // Initialize the database
        serviceAuthorityRepository.saveAndFlush(serviceAuthority);

        int databaseSizeBeforeUpdate = serviceAuthorityRepository.findAll().size();

        // Update the serviceAuthority
        ServiceAuthority updatedServiceAuthority = serviceAuthorityRepository.findById(serviceAuthority.getId()).get();
        // Disconnect from session so that the updates on updatedServiceAuthority are not directly saved in db
        em.detach(updatedServiceAuthority);
        updatedServiceAuthority
            .districtName(UPDATED_DISTRICT_NAME)
            .authorityName(UPDATED_AUTHORITY_NAME)
            .phone(UPDATED_PHONE);
        ServiceAuthorityDTO serviceAuthorityDTO = serviceAuthorityMapper.toDto(updatedServiceAuthority);

        restServiceAuthorityMockMvc.perform(put("/api/service-authorities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceAuthorityDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceAuthority in the database
        List<ServiceAuthority> serviceAuthorityList = serviceAuthorityRepository.findAll();
        assertThat(serviceAuthorityList).hasSize(databaseSizeBeforeUpdate);
        ServiceAuthority testServiceAuthority = serviceAuthorityList.get(serviceAuthorityList.size() - 1);
        assertThat(testServiceAuthority.getDistrictName()).isEqualTo(UPDATED_DISTRICT_NAME);
        assertThat(testServiceAuthority.getAuthorityName()).isEqualTo(UPDATED_AUTHORITY_NAME);
        assertThat(testServiceAuthority.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceAuthority() throws Exception {
        int databaseSizeBeforeUpdate = serviceAuthorityRepository.findAll().size();

        // Create the ServiceAuthority
        ServiceAuthorityDTO serviceAuthorityDTO = serviceAuthorityMapper.toDto(serviceAuthority);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceAuthorityMockMvc.perform(put("/api/service-authorities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceAuthorityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceAuthority in the database
        List<ServiceAuthority> serviceAuthorityList = serviceAuthorityRepository.findAll();
        assertThat(serviceAuthorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceAuthority() throws Exception {
        // Initialize the database
        serviceAuthorityRepository.saveAndFlush(serviceAuthority);

        int databaseSizeBeforeDelete = serviceAuthorityRepository.findAll().size();

        // Delete the serviceAuthority
        restServiceAuthorityMockMvc.perform(delete("/api/service-authorities/{id}", serviceAuthority.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ServiceAuthority> serviceAuthorityList = serviceAuthorityRepository.findAll();
        assertThat(serviceAuthorityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceAuthority.class);
        ServiceAuthority serviceAuthority1 = new ServiceAuthority();
        serviceAuthority1.setId(1L);
        ServiceAuthority serviceAuthority2 = new ServiceAuthority();
        serviceAuthority2.setId(serviceAuthority1.getId());
        assertThat(serviceAuthority1).isEqualTo(serviceAuthority2);
        serviceAuthority2.setId(2L);
        assertThat(serviceAuthority1).isNotEqualTo(serviceAuthority2);
        serviceAuthority1.setId(null);
        assertThat(serviceAuthority1).isNotEqualTo(serviceAuthority2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceAuthorityDTO.class);
        ServiceAuthorityDTO serviceAuthorityDTO1 = new ServiceAuthorityDTO();
        serviceAuthorityDTO1.setId(1L);
        ServiceAuthorityDTO serviceAuthorityDTO2 = new ServiceAuthorityDTO();
        assertThat(serviceAuthorityDTO1).isNotEqualTo(serviceAuthorityDTO2);
        serviceAuthorityDTO2.setId(serviceAuthorityDTO1.getId());
        assertThat(serviceAuthorityDTO1).isEqualTo(serviceAuthorityDTO2);
        serviceAuthorityDTO2.setId(2L);
        assertThat(serviceAuthorityDTO1).isNotEqualTo(serviceAuthorityDTO2);
        serviceAuthorityDTO1.setId(null);
        assertThat(serviceAuthorityDTO1).isNotEqualTo(serviceAuthorityDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(serviceAuthorityMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(serviceAuthorityMapper.fromId(null)).isNull();
    }
}

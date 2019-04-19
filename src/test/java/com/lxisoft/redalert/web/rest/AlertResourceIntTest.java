package com.lxisoft.redalert.web.rest;

import com.lxisoft.redalert.RedalertApp;

import com.lxisoft.redalert.domain.Alert;
import com.lxisoft.redalert.repository.AlertRepository;
import com.lxisoft.redalert.service.AlertService;
import com.lxisoft.redalert.service.dto.AlertDTO;
import com.lxisoft.redalert.service.mapper.AlertMapper;
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

import com.lxisoft.redalert.domain.enumeration.AlertType;
/**
 * Test class for the AlertResource REST controller.
 *
 * @see AlertResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedalertApp.class)
public class AlertResourceIntTest {

    private static final AlertType DEFAULT_TYPE = AlertType.RED;
    private static final AlertType UPDATED_TYPE = AlertType.ORANGE;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private AlertMapper alertMapper;

    @Autowired
    private AlertService alertService;

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

    private MockMvc restAlertMockMvc;

    private Alert alert;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AlertResource alertResource = new AlertResource(alertService);
        this.restAlertMockMvc = MockMvcBuilders.standaloneSetup(alertResource)
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
    public static Alert createEntity(EntityManager em) {
        Alert alert = new Alert()
            .type(DEFAULT_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .status(DEFAULT_STATUS);
        return alert;
    }

    @Before
    public void initTest() {
        alert = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlert() throws Exception {
        int databaseSizeBeforeCreate = alertRepository.findAll().size();

        // Create the Alert
        AlertDTO alertDTO = alertMapper.toDto(alert);
        restAlertMockMvc.perform(post("/api/alerts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alertDTO)))
            .andExpect(status().isCreated());

        // Validate the Alert in the database
        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeCreate + 1);
        Alert testAlert = alertList.get(alertList.size() - 1);
        assertThat(testAlert.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAlert.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAlert.isStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createAlertWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alertRepository.findAll().size();

        // Create the Alert with an existing ID
        alert.setId(1L);
        AlertDTO alertDTO = alertMapper.toDto(alert);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlertMockMvc.perform(post("/api/alerts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alertDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alert in the database
        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAlerts() throws Exception {
        // Initialize the database
        alertRepository.saveAndFlush(alert);

        // Get all the alertList
        restAlertMockMvc.perform(get("/api/alerts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alert.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getAlert() throws Exception {
        // Initialize the database
        alertRepository.saveAndFlush(alert);

        // Get the alert
        restAlertMockMvc.perform(get("/api/alerts/{id}", alert.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(alert.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAlert() throws Exception {
        // Get the alert
        restAlertMockMvc.perform(get("/api/alerts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlert() throws Exception {
        // Initialize the database
        alertRepository.saveAndFlush(alert);

        int databaseSizeBeforeUpdate = alertRepository.findAll().size();

        // Update the alert
        Alert updatedAlert = alertRepository.findById(alert.getId()).get();
        // Disconnect from session so that the updates on updatedAlert are not directly saved in db
        em.detach(updatedAlert);
        updatedAlert
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS);
        AlertDTO alertDTO = alertMapper.toDto(updatedAlert);

        restAlertMockMvc.perform(put("/api/alerts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alertDTO)))
            .andExpect(status().isOk());

        // Validate the Alert in the database
        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeUpdate);
        Alert testAlert = alertList.get(alertList.size() - 1);
        assertThat(testAlert.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAlert.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAlert.isStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingAlert() throws Exception {
        int databaseSizeBeforeUpdate = alertRepository.findAll().size();

        // Create the Alert
        AlertDTO alertDTO = alertMapper.toDto(alert);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlertMockMvc.perform(put("/api/alerts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alertDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alert in the database
        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlert() throws Exception {
        // Initialize the database
        alertRepository.saveAndFlush(alert);

        int databaseSizeBeforeDelete = alertRepository.findAll().size();

        // Delete the alert
        restAlertMockMvc.perform(delete("/api/alerts/{id}", alert.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Alert.class);
        Alert alert1 = new Alert();
        alert1.setId(1L);
        Alert alert2 = new Alert();
        alert2.setId(alert1.getId());
        assertThat(alert1).isEqualTo(alert2);
        alert2.setId(2L);
        assertThat(alert1).isNotEqualTo(alert2);
        alert1.setId(null);
        assertThat(alert1).isNotEqualTo(alert2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlertDTO.class);
        AlertDTO alertDTO1 = new AlertDTO();
        alertDTO1.setId(1L);
        AlertDTO alertDTO2 = new AlertDTO();
        assertThat(alertDTO1).isNotEqualTo(alertDTO2);
        alertDTO2.setId(alertDTO1.getId());
        assertThat(alertDTO1).isEqualTo(alertDTO2);
        alertDTO2.setId(2L);
        assertThat(alertDTO1).isNotEqualTo(alertDTO2);
        alertDTO1.setId(null);
        assertThat(alertDTO1).isNotEqualTo(alertDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(alertMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(alertMapper.fromId(null)).isNull();
    }
}

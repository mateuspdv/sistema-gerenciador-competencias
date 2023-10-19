package br.com.coresgc.web.rest;

import br.com.coresgc.IntegrationTest;
import br.com.coresgc.domain.Seniority;
import br.com.coresgc.repository.SeniorityRepository;
import br.com.coresgc.service.mapper.SeniorityMapper;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the {@link SeniorityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SeniorityResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/seniorities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SeniorityRepository seniorityRepository;

    @Autowired
    private SeniorityMapper seniorityMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSeniorityMockMvc;

    private Seniority seniority;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Seniority createEntity(EntityManager em) {
        Seniority seniority = Seniority.builder().description(DEFAULT_DESCRIPTION).build();
        return seniority;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Seniority createUpdatedEntity(EntityManager em) {
        Seniority seniority = Seniority.builder().description(UPDATED_DESCRIPTION).build();
        return seniority;
    }

    @BeforeEach
    public void initTest() {
        seniority = createEntity(em);
    }

    @Test
    @Transactional
    void getAllSeniorities() throws Exception {
        // Initialize the database
        seniorityRepository.saveAndFlush(seniority);

        // Get all the seniorityList
        restSeniorityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seniority.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getSeniority() throws Exception {
        // Initialize the database
        seniorityRepository.saveAndFlush(seniority);

        // Get the seniority
        restSeniorityMockMvc
            .perform(get(ENTITY_API_URL_ID, seniority.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(seniority.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingSeniority() throws Exception {
        // Get the seniority
        restSeniorityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

}

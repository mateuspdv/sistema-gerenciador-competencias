package br.com.coresgc.web.rest;

import br.com.coresgc.IntegrationTest;
import br.com.coresgc.domain.Category;
import br.com.coresgc.domain.Competency;
import br.com.coresgc.repository.CompetencyRepository;
import br.com.coresgc.service.dto.CompetencyDTO;
import br.com.coresgc.service.mapper.CompetencyMapper;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the {@link CompetencyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompetencyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault());
    private static final ZonedDateTime SMALLER_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault());

    private static final ZonedDateTime DEFAULT_LAST_UPDATE_DATE = ZonedDateTime.now(ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_LAST_UPDATE_DATE = ZonedDateTime.now(ZoneId.systemDefault());
    private static final ZonedDateTime SMALLER_LAST_UPDATE_DATE = ZonedDateTime.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/competencies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CompetencyRepository competencyRepository;

    @Autowired
    private CompetencyMapper competencyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompetencyMockMvc;

    private Competency competency;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Competency createEntity(EntityManager em) {
        Competency competency = Competency
            .builder()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .creationDate(DEFAULT_CREATION_DATE)
            .lastUpdateDate(DEFAULT_LAST_UPDATE_DATE)
            .build();
        // Add required entity
        Category category;
        if (TestUtil.findAll(em, Category.class).isEmpty()) {
            category = CategoryResourceIT.createEntity(em);
            em.persist(category);
            em.flush();
        } else {
            category = TestUtil.findAll(em, Category.class).get(0);
        }
        competency.setCategory(category);
        return competency;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Competency createUpdatedEntity(EntityManager em) {
        Competency competency = Competency
            .builder()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdateDate(UPDATED_LAST_UPDATE_DATE)
            .build();
        // Add required entity
        Category category;
        if (TestUtil.findAll(em, Category.class).isEmpty()) {
            category = CategoryResourceIT.createUpdatedEntity(em);
            em.persist(category);
            em.flush();
        } else {
            category = TestUtil.findAll(em, Category.class).get(0);
        }
        competency.setCategory(category);
        return competency;
    }

    @BeforeEach
    public void initTest() {
        competency = createEntity(em);
    }

    @Test
    @Transactional
    void createCompetency() throws Exception {
        int databaseSizeBeforeCreate = competencyRepository.findAll().size();
        // Create the Competency
        CompetencyDTO competencyDTO = competencyMapper.toDto(competency);
        restCompetencyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(competencyDTO)))
            .andExpect(status().isCreated());

        // Validate the Competency in the database
        List<Competency> competencyList = competencyRepository.findAll();
        assertThat(competencyList).hasSize(databaseSizeBeforeCreate + 1);
        Competency testCompetency = competencyList.get(competencyList.size() - 1);
        assertThat(testCompetency.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCompetency.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCompetency.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testCompetency.getLastUpdateDate()).isEqualTo(DEFAULT_LAST_UPDATE_DATE);
    }

    @Test
    @Transactional
    void createCompetencyWithExistingId() throws Exception {
        // Create the Competency with an existing ID
        competency.setId(1L);
        CompetencyDTO competencyDTO = competencyMapper.toDto(competency);

        int databaseSizeBeforeCreate = competencyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompetencyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(competencyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Competency in the database
        List<Competency> competencyList = competencyRepository.findAll();
        assertThat(competencyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = competencyRepository.findAll().size();
        // set the field null
        competency.setName(null);

        // Create the Competency, which fails.
        CompetencyDTO competencyDTO = competencyMapper.toDto(competency);

        restCompetencyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(competencyDTO)))
            .andExpect(status().isBadRequest());

        List<Competency> competencyList = competencyRepository.findAll();
        assertThat(competencyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = competencyRepository.findAll().size();
        // set the field null
        competency.setDescription(null);

        // Create the Competency, which fails.
        CompetencyDTO competencyDTO = competencyMapper.toDto(competency);

        restCompetencyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(competencyDTO)))
            .andExpect(status().isBadRequest());

        List<Competency> competencyList = competencyRepository.findAll();
        assertThat(competencyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = competencyRepository.findAll().size();
        // set the field null
        competency.setCreationDate(null);

        // Create the Competency, which fails.
        CompetencyDTO competencyDTO = competencyMapper.toDto(competency);

        restCompetencyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(competencyDTO)))
            .andExpect(status().isBadRequest());

        List<Competency> competencyList = competencyRepository.findAll();
        assertThat(competencyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastUpdateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = competencyRepository.findAll().size();
        // set the field null
        competency.setLastUpdateDate(null);

        // Create the Competency, which fails.
        CompetencyDTO competencyDTO = competencyMapper.toDto(competency);

        restCompetencyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(competencyDTO)))
            .andExpect(status().isBadRequest());

        List<Competency> competencyList = competencyRepository.findAll();
        assertThat(competencyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCompetencies() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get all the competencyList
        restCompetencyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competency.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastUpdateDate").value(hasItem(DEFAULT_LAST_UPDATE_DATE.toString())));
    }

    @Test
    @Transactional
    void getCompetency() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get the competency
        restCompetencyMockMvc
            .perform(get(ENTITY_API_URL_ID, competency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(competency.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.lastUpdateDate").value(DEFAULT_LAST_UPDATE_DATE.toString()));
    }

    @Test
    @Transactional
    void getCompetenciesByIdFiltering() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        Long id = competency.getId();

        defaultCompetencyShouldBeFound("id.equals=" + id);
        defaultCompetencyShouldNotBeFound("id.notEquals=" + id);

        defaultCompetencyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCompetencyShouldNotBeFound("id.greaterThan=" + id);

        defaultCompetencyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCompetencyShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCompetenciesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get all the competencyList where name equals to DEFAULT_NAME
        defaultCompetencyShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the competencyList where name equals to UPDATED_NAME
        defaultCompetencyShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCompetenciesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get all the competencyList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCompetencyShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the competencyList where name equals to UPDATED_NAME
        defaultCompetencyShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCompetenciesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get all the competencyList where name is not null
        defaultCompetencyShouldBeFound("name.specified=true");

        // Get all the competencyList where name is null
        defaultCompetencyShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllCompetenciesByNameContainsSomething() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get all the competencyList where name contains DEFAULT_NAME
        defaultCompetencyShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the competencyList where name contains UPDATED_NAME
        defaultCompetencyShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCompetenciesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get all the competencyList where name does not contain DEFAULT_NAME
        defaultCompetencyShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the competencyList where name does not contain UPDATED_NAME
        defaultCompetencyShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCompetenciesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get all the competencyList where description equals to DEFAULT_DESCRIPTION
        defaultCompetencyShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the competencyList where description equals to UPDATED_DESCRIPTION
        defaultCompetencyShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCompetenciesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get all the competencyList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCompetencyShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the competencyList where description equals to UPDATED_DESCRIPTION
        defaultCompetencyShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCompetenciesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get all the competencyList where description is not null
        defaultCompetencyShouldBeFound("description.specified=true");

        // Get all the competencyList where description is null
        defaultCompetencyShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllCompetenciesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get all the competencyList where description contains DEFAULT_DESCRIPTION
        defaultCompetencyShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the competencyList where description contains UPDATED_DESCRIPTION
        defaultCompetencyShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCompetenciesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get all the competencyList where description does not contain DEFAULT_DESCRIPTION
        defaultCompetencyShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the competencyList where description does not contain UPDATED_DESCRIPTION
        defaultCompetencyShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCompetenciesByCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get all the competencyList where creationDate equals to DEFAULT_CREATION_DATE
        defaultCompetencyShouldBeFound("creationDate.equals=" + DEFAULT_CREATION_DATE);

        // Get all the competencyList where creationDate equals to UPDATED_CREATION_DATE
        defaultCompetencyShouldNotBeFound("creationDate.equals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    void getAllCompetenciesByCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get all the competencyList where creationDate in DEFAULT_CREATION_DATE or UPDATED_CREATION_DATE
        defaultCompetencyShouldBeFound("creationDate.in=" + DEFAULT_CREATION_DATE + "," + UPDATED_CREATION_DATE);

        // Get all the competencyList where creationDate equals to UPDATED_CREATION_DATE
        defaultCompetencyShouldNotBeFound("creationDate.in=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    void getAllCompetenciesByCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get all the competencyList where creationDate is not null
        defaultCompetencyShouldBeFound("creationDate.specified=true");

        // Get all the competencyList where creationDate is null
        defaultCompetencyShouldNotBeFound("creationDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCompetenciesByCreationDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get all the competencyList where creationDate is greater than or equal to DEFAULT_CREATION_DATE
        defaultCompetencyShouldBeFound("creationDate.greaterThanOrEqual=" + DEFAULT_CREATION_DATE);

        // Get all the competencyList where creationDate is greater than or equal to UPDATED_CREATION_DATE
        defaultCompetencyShouldNotBeFound("creationDate.greaterThanOrEqual=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    void getAllCompetenciesByCreationDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get all the competencyList where creationDate is less than or equal to DEFAULT_CREATION_DATE
        defaultCompetencyShouldBeFound("creationDate.lessThanOrEqual=" + DEFAULT_CREATION_DATE);

        // Get all the competencyList where creationDate is less than or equal to SMALLER_CREATION_DATE
        defaultCompetencyShouldNotBeFound("creationDate.lessThanOrEqual=" + SMALLER_CREATION_DATE);
    }

    @Test
    @Transactional
    void getAllCompetenciesByCreationDateIsLessThanSomething() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get all the competencyList where creationDate is less than DEFAULT_CREATION_DATE
        defaultCompetencyShouldNotBeFound("creationDate.lessThan=" + DEFAULT_CREATION_DATE);

        // Get all the competencyList where creationDate is less than UPDATED_CREATION_DATE
        defaultCompetencyShouldBeFound("creationDate.lessThan=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    void getAllCompetenciesByCreationDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get all the competencyList where creationDate is greater than DEFAULT_CREATION_DATE
        defaultCompetencyShouldNotBeFound("creationDate.greaterThan=" + DEFAULT_CREATION_DATE);

        // Get all the competencyList where creationDate is greater than SMALLER_CREATION_DATE
        defaultCompetencyShouldBeFound("creationDate.greaterThan=" + SMALLER_CREATION_DATE);
    }

    @Test
    @Transactional
    void getAllCompetenciesByLastUpdateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get all the competencyList where lastUpdateDate equals to DEFAULT_LAST_UPDATE_DATE
        defaultCompetencyShouldBeFound("lastUpdateDate.equals=" + DEFAULT_LAST_UPDATE_DATE);

        // Get all the competencyList where lastUpdateDate equals to UPDATED_LAST_UPDATE_DATE
        defaultCompetencyShouldNotBeFound("lastUpdateDate.equals=" + UPDATED_LAST_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllCompetenciesByLastUpdateDateIsInShouldWork() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get all the competencyList where lastUpdateDate in DEFAULT_LAST_UPDATE_DATE or UPDATED_LAST_UPDATE_DATE
        defaultCompetencyShouldBeFound("lastUpdateDate.in=" + DEFAULT_LAST_UPDATE_DATE + "," + UPDATED_LAST_UPDATE_DATE);

        // Get all the competencyList where lastUpdateDate equals to UPDATED_LAST_UPDATE_DATE
        defaultCompetencyShouldNotBeFound("lastUpdateDate.in=" + UPDATED_LAST_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllCompetenciesByLastUpdateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get all the competencyList where lastUpdateDate is not null
        defaultCompetencyShouldBeFound("lastUpdateDate.specified=true");

        // Get all the competencyList where lastUpdateDate is null
        defaultCompetencyShouldNotBeFound("lastUpdateDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCompetenciesByLastUpdateDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get all the competencyList where lastUpdateDate is greater than or equal to DEFAULT_LAST_UPDATE_DATE
        defaultCompetencyShouldBeFound("lastUpdateDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATE_DATE);

        // Get all the competencyList where lastUpdateDate is greater than or equal to UPDATED_LAST_UPDATE_DATE
        defaultCompetencyShouldNotBeFound("lastUpdateDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllCompetenciesByLastUpdateDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get all the competencyList where lastUpdateDate is less than or equal to DEFAULT_LAST_UPDATE_DATE
        defaultCompetencyShouldBeFound("lastUpdateDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATE_DATE);

        // Get all the competencyList where lastUpdateDate is less than or equal to SMALLER_LAST_UPDATE_DATE
        defaultCompetencyShouldNotBeFound("lastUpdateDate.lessThanOrEqual=" + SMALLER_LAST_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllCompetenciesByLastUpdateDateIsLessThanSomething() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get all the competencyList where lastUpdateDate is less than DEFAULT_LAST_UPDATE_DATE
        defaultCompetencyShouldNotBeFound("lastUpdateDate.lessThan=" + DEFAULT_LAST_UPDATE_DATE);

        // Get all the competencyList where lastUpdateDate is less than UPDATED_LAST_UPDATE_DATE
        defaultCompetencyShouldBeFound("lastUpdateDate.lessThan=" + UPDATED_LAST_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllCompetenciesByLastUpdateDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get all the competencyList where lastUpdateDate is greater than DEFAULT_LAST_UPDATE_DATE
        defaultCompetencyShouldNotBeFound("lastUpdateDate.greaterThan=" + DEFAULT_LAST_UPDATE_DATE);

        // Get all the competencyList where lastUpdateDate is greater than SMALLER_LAST_UPDATE_DATE
        defaultCompetencyShouldBeFound("lastUpdateDate.greaterThan=" + SMALLER_LAST_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllCompetenciesByCategoryIsEqualToSomething() throws Exception {
        Category category;
        if (TestUtil.findAll(em, Category.class).isEmpty()) {
            competencyRepository.saveAndFlush(competency);
            category = CategoryResourceIT.createEntity(em);
        } else {
            category = TestUtil.findAll(em, Category.class).get(0);
        }
        em.persist(category);
        em.flush();
        competency.setCategory(category);
        competencyRepository.saveAndFlush(competency);
        Long categoryId = category.getId();
        // Get all the competencyList where category equals to categoryId
        defaultCompetencyShouldBeFound("categoryId.equals=" + categoryId);

        // Get all the competencyList where category equals to (categoryId + 1)
        defaultCompetencyShouldNotBeFound("categoryId.equals=" + (categoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCompetencyShouldBeFound(String filter) throws Exception {
        restCompetencyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competency.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastUpdateDate").value(hasItem(DEFAULT_LAST_UPDATE_DATE.toString())));

        // Check, that the count call also returns 1
        restCompetencyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCompetencyShouldNotBeFound(String filter) throws Exception {
        restCompetencyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCompetencyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCompetency() throws Exception {
        // Get the competency
        restCompetencyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCompetency() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        int databaseSizeBeforeUpdate = competencyRepository.findAll().size();

        // Update the competency
        Competency updatedCompetency = competencyRepository.findById(competency.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCompetency are not directly saved in db
        em.detach(updatedCompetency);
        updatedCompetency.setName(UPDATED_NAME);
        updatedCompetency.setDescription(UPDATED_DESCRIPTION);
        updatedCompetency.setCreationDate(UPDATED_CREATION_DATE);
        updatedCompetency.setLastUpdateDate(UPDATED_LAST_UPDATE_DATE);
        CompetencyDTO competencyDTO = competencyMapper.toDto(updatedCompetency);

        restCompetencyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, competencyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competencyDTO))
            )
            .andExpect(status().isOk());

        // Validate the Competency in the database
        List<Competency> competencyList = competencyRepository.findAll();
        assertThat(competencyList).hasSize(databaseSizeBeforeUpdate);
        Competency testCompetency = competencyList.get(competencyList.size() - 1);
        assertThat(testCompetency.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompetency.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCompetency.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testCompetency.getLastUpdateDate()).isEqualTo(UPDATED_LAST_UPDATE_DATE);
    }

    @Test
    @Transactional
    void putNonExistingCompetency() throws Exception {
        int databaseSizeBeforeUpdate = competencyRepository.findAll().size();
        competency.setId(count.incrementAndGet());

        // Create the Competency
        CompetencyDTO competencyDTO = competencyMapper.toDto(competency);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetencyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, competencyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competencyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competency in the database
        List<Competency> competencyList = competencyRepository.findAll();
        assertThat(competencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompetency() throws Exception {
        int databaseSizeBeforeUpdate = competencyRepository.findAll().size();
        competency.setId(count.incrementAndGet());

        // Create the Competency
        CompetencyDTO competencyDTO = competencyMapper.toDto(competency);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetencyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competencyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competency in the database
        List<Competency> competencyList = competencyRepository.findAll();
        assertThat(competencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompetency() throws Exception {
        int databaseSizeBeforeUpdate = competencyRepository.findAll().size();
        competency.setId(count.incrementAndGet());

        // Create the Competency
        CompetencyDTO competencyDTO = competencyMapper.toDto(competency);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetencyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(competencyDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Competency in the database
        List<Competency> competencyList = competencyRepository.findAll();
        assertThat(competencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompetencyWithPatch() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        int databaseSizeBeforeUpdate = competencyRepository.findAll().size();

        // Update the competency using partial update
        Competency partialUpdatedCompetency = new Competency();
        partialUpdatedCompetency.setId(competency.getId());

        partialUpdatedCompetency.setName(UPDATED_NAME);
        partialUpdatedCompetency.setLastUpdateDate(UPDATED_LAST_UPDATE_DATE);

        restCompetencyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompetency.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompetency))
            )
            .andExpect(status().isOk());

        // Validate the Competency in the database
        List<Competency> competencyList = competencyRepository.findAll();
        assertThat(competencyList).hasSize(databaseSizeBeforeUpdate);
        Competency testCompetency = competencyList.get(competencyList.size() - 1);
        assertThat(testCompetency.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompetency.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCompetency.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testCompetency.getLastUpdateDate()).isEqualTo(UPDATED_LAST_UPDATE_DATE);
    }

    @Test
    @Transactional
    void fullUpdateCompetencyWithPatch() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        int databaseSizeBeforeUpdate = competencyRepository.findAll().size();

        // Update the competency using partial update
        Competency partialUpdatedCompetency = new Competency();
        partialUpdatedCompetency.setId(competency.getId());

        partialUpdatedCompetency.setName(UPDATED_NAME);
        partialUpdatedCompetency.setDescription(UPDATED_DESCRIPTION);
        partialUpdatedCompetency.setCreationDate(UPDATED_CREATION_DATE);
        partialUpdatedCompetency.setLastUpdateDate(UPDATED_LAST_UPDATE_DATE);

        restCompetencyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompetency.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompetency))
            )
            .andExpect(status().isOk());

        // Validate the Competency in the database
        List<Competency> competencyList = competencyRepository.findAll();
        assertThat(competencyList).hasSize(databaseSizeBeforeUpdate);
        Competency testCompetency = competencyList.get(competencyList.size() - 1);
        assertThat(testCompetency.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompetency.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCompetency.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testCompetency.getLastUpdateDate()).isEqualTo(UPDATED_LAST_UPDATE_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingCompetency() throws Exception {
        int databaseSizeBeforeUpdate = competencyRepository.findAll().size();
        competency.setId(count.incrementAndGet());

        // Create the Competency
        CompetencyDTO competencyDTO = competencyMapper.toDto(competency);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetencyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, competencyDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(competencyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competency in the database
        List<Competency> competencyList = competencyRepository.findAll();
        assertThat(competencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompetency() throws Exception {
        int databaseSizeBeforeUpdate = competencyRepository.findAll().size();
        competency.setId(count.incrementAndGet());

        // Create the Competency
        CompetencyDTO competencyDTO = competencyMapper.toDto(competency);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetencyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(competencyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competency in the database
        List<Competency> competencyList = competencyRepository.findAll();
        assertThat(competencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompetency() throws Exception {
        int databaseSizeBeforeUpdate = competencyRepository.findAll().size();
        competency.setId(count.incrementAndGet());

        // Create the Competency
        CompetencyDTO competencyDTO = competencyMapper.toDto(competency);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetencyMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(competencyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Competency in the database
        List<Competency> competencyList = competencyRepository.findAll();
        assertThat(competencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompetency() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        int databaseSizeBeforeDelete = competencyRepository.findAll().size();

        // Delete the competency
        restCompetencyMockMvc
            .perform(delete(ENTITY_API_URL_ID, competency.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Competency> competencyList = competencyRepository.findAll();
        assertThat(competencyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

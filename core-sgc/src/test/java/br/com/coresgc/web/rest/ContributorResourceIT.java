package br.com.coresgc.web.rest;

import br.com.coresgc.IntegrationTest;
import br.com.coresgc.domain.Competency;
import br.com.coresgc.domain.Contributor;
import br.com.coresgc.domain.Seniority;
import br.com.coresgc.repository.ContributorRepository;
import br.com.coresgc.service.ContributorService;
import br.com.coresgc.service.dto.ContributorDTO;
import br.com.coresgc.service.mapper.ContributorMapper;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import static br.com.coresgc.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the {@link ContributorResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ContributorResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CPF = "AAAAAAAAAAA";
    private static final String UPDATED_CPF = "BBBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_BIRTH_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_ADMISSION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ADMISSION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_ADMISSION_DATE = LocalDate.ofEpochDay(-1L);

    private static final ZonedDateTime DEFAULT_CREATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_CREATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_LAST_UPDATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LAST_UPDATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String ENTITY_API_URL = "/api/contributors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ContributorRepository contributorRepository;

    @Mock
    private ContributorRepository contributorRepositoryMock;

    @Autowired
    private ContributorMapper contributorMapper;

    @Mock
    private ContributorService contributorServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContributorMockMvc;

    private Contributor contributor;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contributor createEntity(EntityManager em) {
        Contributor contributor = Contributor.builder()
            .name(DEFAULT_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .cpf(DEFAULT_CPF)
            .email(DEFAULT_EMAIL)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE)
            .birthDate(DEFAULT_BIRTH_DATE)
            .admissionDate(DEFAULT_ADMISSION_DATE)
            .creationDate(DEFAULT_CREATION_DATE)
            .lastUpdateDate(DEFAULT_LAST_UPDATE_DATE).build();
        // Add required entity
        Seniority seniority;
        if (TestUtil.findAll(em, Seniority.class).isEmpty()) {
            seniority = SeniorityResourceIT.createEntity(em);
            em.persist(seniority);
            em.flush();
        } else {
            seniority = TestUtil.findAll(em, Seniority.class).get(0);
        }
        contributor.setSeniority(seniority);
        // Add required entity
        Competency competency;
        if (TestUtil.findAll(em, Competency.class).isEmpty()) {
            competency = CompetencyResourceIT.createEntity(em);
            em.persist(competency);
            em.flush();
        } else {
            competency = TestUtil.findAll(em, Competency.class).get(0);
        }
        contributor.getCompetences().add(competency);
        return contributor;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contributor createUpdatedEntity(EntityManager em) {
        Contributor contributor = Contributor.builder()
            .name(UPDATED_NAME)
            .lastName(UPDATED_LAST_NAME)
            .cpf(UPDATED_CPF)
            .email(UPDATED_EMAIL)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .birthDate(UPDATED_BIRTH_DATE)
            .admissionDate(UPDATED_ADMISSION_DATE)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdateDate(UPDATED_LAST_UPDATE_DATE).build();
        // Add required entity
        Seniority seniority;
        if (TestUtil.findAll(em, Seniority.class).isEmpty()) {
            seniority = SeniorityResourceIT.createUpdatedEntity(em);
            em.persist(seniority);
            em.flush();
        } else {
            seniority = TestUtil.findAll(em, Seniority.class).get(0);
        }
        contributor.setSeniority(seniority);
        // Add required entity
        Competency competency;
        if (TestUtil.findAll(em, Competency.class).isEmpty()) {
            competency = CompetencyResourceIT.createUpdatedEntity(em);
            em.persist(competency);
            em.flush();
        } else {
            competency = TestUtil.findAll(em, Competency.class).get(0);
        }
        contributor.getCompetences().add(competency);
        return contributor;
    }

    @BeforeEach
    public void initTest() {
        contributor = createEntity(em);
    }

    @Test
    @Transactional
    void createContributor() throws Exception {
        int databaseSizeBeforeCreate = contributorRepository.findAll().size();
        // Create the Contributor
        ContributorDTO contributorDTO = contributorMapper.toDto(contributor);
        restContributorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contributorDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Contributor in the database
        List<Contributor> contributorList = contributorRepository.findAll();
        assertThat(contributorList).hasSize(databaseSizeBeforeCreate + 1);
        Contributor testContributor = contributorList.get(contributorList.size() - 1);
        assertThat(testContributor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testContributor.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testContributor.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testContributor.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testContributor.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testContributor.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testContributor.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testContributor.getAdmissionDate()).isEqualTo(DEFAULT_ADMISSION_DATE);
        assertThat(testContributor.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testContributor.getLastUpdateDate()).isEqualTo(DEFAULT_LAST_UPDATE_DATE);
    }

    @Test
    @Transactional
    void createContributorWithExistingId() throws Exception {
        // Create the Contributor with an existing ID
        contributor.setId(1L);
        ContributorDTO contributorDTO = contributorMapper.toDto(contributor);

        int databaseSizeBeforeCreate = contributorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContributorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contributorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contributor in the database
        List<Contributor> contributorList = contributorRepository.findAll();
        assertThat(contributorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = contributorRepository.findAll().size();
        // set the field null
        contributor.setName(null);

        // Create the Contributor, which fails.
        ContributorDTO contributorDTO = contributorMapper.toDto(contributor);

        restContributorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contributorDTO))
            )
            .andExpect(status().isBadRequest());

        List<Contributor> contributorList = contributorRepository.findAll();
        assertThat(contributorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = contributorRepository.findAll().size();
        // set the field null
        contributor.setLastName(null);

        // Create the Contributor, which fails.
        ContributorDTO contributorDTO = contributorMapper.toDto(contributor);

        restContributorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contributorDTO))
            )
            .andExpect(status().isBadRequest());

        List<Contributor> contributorList = contributorRepository.findAll();
        assertThat(contributorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCpfIsRequired() throws Exception {
        int databaseSizeBeforeTest = contributorRepository.findAll().size();
        // set the field null
        contributor.setCpf(null);

        // Create the Contributor, which fails.
        ContributorDTO contributorDTO = contributorMapper.toDto(contributor);

        restContributorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contributorDTO))
            )
            .andExpect(status().isBadRequest());

        List<Contributor> contributorList = contributorRepository.findAll();
        assertThat(contributorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = contributorRepository.findAll().size();
        // set the field null
        contributor.setEmail(null);

        // Create the Contributor, which fails.
        ContributorDTO contributorDTO = contributorMapper.toDto(contributor);

        restContributorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contributorDTO))
            )
            .andExpect(status().isBadRequest());

        List<Contributor> contributorList = contributorRepository.findAll();
        assertThat(contributorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBirthDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = contributorRepository.findAll().size();
        // set the field null
        contributor.setBirthDate(null);

        // Create the Contributor, which fails.
        ContributorDTO contributorDTO = contributorMapper.toDto(contributor);

        restContributorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contributorDTO))
            )
            .andExpect(status().isBadRequest());

        List<Contributor> contributorList = contributorRepository.findAll();
        assertThat(contributorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAdmissionDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = contributorRepository.findAll().size();
        // set the field null
        contributor.setAdmissionDate(null);

        // Create the Contributor, which fails.
        ContributorDTO contributorDTO = contributorMapper.toDto(contributor);

        restContributorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contributorDTO))
            )
            .andExpect(status().isBadRequest());

        List<Contributor> contributorList = contributorRepository.findAll();
        assertThat(contributorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = contributorRepository.findAll().size();
        // set the field null
        contributor.setCreationDate(null);

        // Create the Contributor, which fails.
        ContributorDTO contributorDTO = contributorMapper.toDto(contributor);

        restContributorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contributorDTO))
            )
            .andExpect(status().isBadRequest());

        List<Contributor> contributorList = contributorRepository.findAll();
        assertThat(contributorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastUpdateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = contributorRepository.findAll().size();
        // set the field null
        contributor.setLastUpdateDate(null);

        // Create the Contributor, which fails.
        ContributorDTO contributorDTO = contributorMapper.toDto(contributor);

        restContributorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contributorDTO))
            )
            .andExpect(status().isBadRequest());

        List<Contributor> contributorList = contributorRepository.findAll();
        assertThat(contributorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllContributors() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList
        restContributorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contributor.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].admissionDate").value(hasItem(DEFAULT_ADMISSION_DATE.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(sameInstant(DEFAULT_CREATION_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdateDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATE_DATE))));
    }

    @SuppressWarnings({"unchecked"})
    void getAllContributorsWithEagerRelationshipsIsEnabled() throws Exception {
        when(contributorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restContributorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(contributorServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    void getAllContributorsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(contributorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restContributorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(contributorRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getContributor() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get the contributor
        restContributorMockMvc
            .perform(get(ENTITY_API_URL_ID, contributor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contributor.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64.getEncoder().encodeToString(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.admissionDate").value(DEFAULT_ADMISSION_DATE.toString()))
            .andExpect(jsonPath("$.creationDate").value(sameInstant(DEFAULT_CREATION_DATE)))
            .andExpect(jsonPath("$.lastUpdateDate").value(sameInstant(DEFAULT_LAST_UPDATE_DATE)));
    }

    @Test
    @Transactional
    void getContributorsByIdFiltering() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        Long id = contributor.getId();

        defaultContributorShouldBeFound("id.equals=" + id);
        defaultContributorShouldNotBeFound("id.notEquals=" + id);

        defaultContributorShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultContributorShouldNotBeFound("id.greaterThan=" + id);

        defaultContributorShouldBeFound("id.lessThanOrEqual=" + id);
        defaultContributorShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllContributorsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where name equals to DEFAULT_NAME
        defaultContributorShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the contributorList where name equals to UPDATED_NAME
        defaultContributorShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllContributorsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where name in DEFAULT_NAME or UPDATED_NAME
        defaultContributorShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the contributorList where name equals to UPDATED_NAME
        defaultContributorShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllContributorsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where name is not null
        defaultContributorShouldBeFound("name.specified=true");

        // Get all the contributorList where name is null
        defaultContributorShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllContributorsByNameContainsSomething() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where name contains DEFAULT_NAME
        defaultContributorShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the contributorList where name contains UPDATED_NAME
        defaultContributorShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllContributorsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where name does not contain DEFAULT_NAME
        defaultContributorShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the contributorList where name does not contain UPDATED_NAME
        defaultContributorShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllContributorsByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where lastName equals to DEFAULT_LAST_NAME
        defaultContributorShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the contributorList where lastName equals to UPDATED_LAST_NAME
        defaultContributorShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllContributorsByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultContributorShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the contributorList where lastName equals to UPDATED_LAST_NAME
        defaultContributorShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllContributorsByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where lastName is not null
        defaultContributorShouldBeFound("lastName.specified=true");

        // Get all the contributorList where lastName is null
        defaultContributorShouldNotBeFound("lastName.specified=false");
    }

    @Test
    @Transactional
    void getAllContributorsByLastNameContainsSomething() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where lastName contains DEFAULT_LAST_NAME
        defaultContributorShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the contributorList where lastName contains UPDATED_LAST_NAME
        defaultContributorShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllContributorsByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where lastName does not contain DEFAULT_LAST_NAME
        defaultContributorShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the contributorList where lastName does not contain UPDATED_LAST_NAME
        defaultContributorShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllContributorsByCpfIsEqualToSomething() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where cpf equals to DEFAULT_CPF
        defaultContributorShouldBeFound("cpf.equals=" + DEFAULT_CPF);

        // Get all the contributorList where cpf equals to UPDATED_CPF
        defaultContributorShouldNotBeFound("cpf.equals=" + UPDATED_CPF);
    }

    @Test
    @Transactional
    void getAllContributorsByCpfIsInShouldWork() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where cpf in DEFAULT_CPF or UPDATED_CPF
        defaultContributorShouldBeFound("cpf.in=" + DEFAULT_CPF + "," + UPDATED_CPF);

        // Get all the contributorList where cpf equals to UPDATED_CPF
        defaultContributorShouldNotBeFound("cpf.in=" + UPDATED_CPF);
    }

    @Test
    @Transactional
    void getAllContributorsByCpfIsNullOrNotNull() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where cpf is not null
        defaultContributorShouldBeFound("cpf.specified=true");

        // Get all the contributorList where cpf is null
        defaultContributorShouldNotBeFound("cpf.specified=false");
    }

    @Test
    @Transactional
    void getAllContributorsByCpfContainsSomething() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where cpf contains DEFAULT_CPF
        defaultContributorShouldBeFound("cpf.contains=" + DEFAULT_CPF);

        // Get all the contributorList where cpf contains UPDATED_CPF
        defaultContributorShouldNotBeFound("cpf.contains=" + UPDATED_CPF);
    }

    @Test
    @Transactional
    void getAllContributorsByCpfNotContainsSomething() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where cpf does not contain DEFAULT_CPF
        defaultContributorShouldNotBeFound("cpf.doesNotContain=" + DEFAULT_CPF);

        // Get all the contributorList where cpf does not contain UPDATED_CPF
        defaultContributorShouldBeFound("cpf.doesNotContain=" + UPDATED_CPF);
    }

    @Test
    @Transactional
    void getAllContributorsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where email equals to DEFAULT_EMAIL
        defaultContributorShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the contributorList where email equals to UPDATED_EMAIL
        defaultContributorShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllContributorsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultContributorShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the contributorList where email equals to UPDATED_EMAIL
        defaultContributorShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllContributorsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where email is not null
        defaultContributorShouldBeFound("email.specified=true");

        // Get all the contributorList where email is null
        defaultContributorShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllContributorsByEmailContainsSomething() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where email contains DEFAULT_EMAIL
        defaultContributorShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the contributorList where email contains UPDATED_EMAIL
        defaultContributorShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllContributorsByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where email does not contain DEFAULT_EMAIL
        defaultContributorShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the contributorList where email does not contain UPDATED_EMAIL
        defaultContributorShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllContributorsByBirthDateIsEqualToSomething() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where birthDate equals to DEFAULT_BIRTH_DATE
        defaultContributorShouldBeFound("birthDate.equals=" + DEFAULT_BIRTH_DATE);

        // Get all the contributorList where birthDate equals to UPDATED_BIRTH_DATE
        defaultContributorShouldNotBeFound("birthDate.equals=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    void getAllContributorsByBirthDateIsInShouldWork() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where birthDate in DEFAULT_BIRTH_DATE or UPDATED_BIRTH_DATE
        defaultContributorShouldBeFound("birthDate.in=" + DEFAULT_BIRTH_DATE + "," + UPDATED_BIRTH_DATE);

        // Get all the contributorList where birthDate equals to UPDATED_BIRTH_DATE
        defaultContributorShouldNotBeFound("birthDate.in=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    void getAllContributorsByBirthDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where birthDate is not null
        defaultContributorShouldBeFound("birthDate.specified=true");

        // Get all the contributorList where birthDate is null
        defaultContributorShouldNotBeFound("birthDate.specified=false");
    }

    @Test
    @Transactional
    void getAllContributorsByBirthDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where birthDate is greater than or equal to DEFAULT_BIRTH_DATE
        defaultContributorShouldBeFound("birthDate.greaterThanOrEqual=" + DEFAULT_BIRTH_DATE);

        // Get all the contributorList where birthDate is greater than or equal to UPDATED_BIRTH_DATE
        defaultContributorShouldNotBeFound("birthDate.greaterThanOrEqual=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    void getAllContributorsByBirthDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where birthDate is less than or equal to DEFAULT_BIRTH_DATE
        defaultContributorShouldBeFound("birthDate.lessThanOrEqual=" + DEFAULT_BIRTH_DATE);

        // Get all the contributorList where birthDate is less than or equal to SMALLER_BIRTH_DATE
        defaultContributorShouldNotBeFound("birthDate.lessThanOrEqual=" + SMALLER_BIRTH_DATE);
    }

    @Test
    @Transactional
    void getAllContributorsByBirthDateIsLessThanSomething() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where birthDate is less than DEFAULT_BIRTH_DATE
        defaultContributorShouldNotBeFound("birthDate.lessThan=" + DEFAULT_BIRTH_DATE);

        // Get all the contributorList where birthDate is less than UPDATED_BIRTH_DATE
        defaultContributorShouldBeFound("birthDate.lessThan=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    void getAllContributorsByBirthDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where birthDate is greater than DEFAULT_BIRTH_DATE
        defaultContributorShouldNotBeFound("birthDate.greaterThan=" + DEFAULT_BIRTH_DATE);

        // Get all the contributorList where birthDate is greater than SMALLER_BIRTH_DATE
        defaultContributorShouldBeFound("birthDate.greaterThan=" + SMALLER_BIRTH_DATE);
    }

    @Test
    @Transactional
    void getAllContributorsByAdmissionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where admissionDate equals to DEFAULT_ADMISSION_DATE
        defaultContributorShouldBeFound("admissionDate.equals=" + DEFAULT_ADMISSION_DATE);

        // Get all the contributorList where admissionDate equals to UPDATED_ADMISSION_DATE
        defaultContributorShouldNotBeFound("admissionDate.equals=" + UPDATED_ADMISSION_DATE);
    }

    @Test
    @Transactional
    void getAllContributorsByAdmissionDateIsInShouldWork() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where admissionDate in DEFAULT_ADMISSION_DATE or UPDATED_ADMISSION_DATE
        defaultContributorShouldBeFound("admissionDate.in=" + DEFAULT_ADMISSION_DATE + "," + UPDATED_ADMISSION_DATE);

        // Get all the contributorList where admissionDate equals to UPDATED_ADMISSION_DATE
        defaultContributorShouldNotBeFound("admissionDate.in=" + UPDATED_ADMISSION_DATE);
    }

    @Test
    @Transactional
    void getAllContributorsByAdmissionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where admissionDate is not null
        defaultContributorShouldBeFound("admissionDate.specified=true");

        // Get all the contributorList where admissionDate is null
        defaultContributorShouldNotBeFound("admissionDate.specified=false");
    }

    @Test
    @Transactional
    void getAllContributorsByAdmissionDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where admissionDate is greater than or equal to DEFAULT_ADMISSION_DATE
        defaultContributorShouldBeFound("admissionDate.greaterThanOrEqual=" + DEFAULT_ADMISSION_DATE);

        // Get all the contributorList where admissionDate is greater than or equal to UPDATED_ADMISSION_DATE
        defaultContributorShouldNotBeFound("admissionDate.greaterThanOrEqual=" + UPDATED_ADMISSION_DATE);
    }

    @Test
    @Transactional
    void getAllContributorsByAdmissionDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where admissionDate is less than or equal to DEFAULT_ADMISSION_DATE
        defaultContributorShouldBeFound("admissionDate.lessThanOrEqual=" + DEFAULT_ADMISSION_DATE);

        // Get all the contributorList where admissionDate is less than or equal to SMALLER_ADMISSION_DATE
        defaultContributorShouldNotBeFound("admissionDate.lessThanOrEqual=" + SMALLER_ADMISSION_DATE);
    }

    @Test
    @Transactional
    void getAllContributorsByAdmissionDateIsLessThanSomething() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where admissionDate is less than DEFAULT_ADMISSION_DATE
        defaultContributorShouldNotBeFound("admissionDate.lessThan=" + DEFAULT_ADMISSION_DATE);

        // Get all the contributorList where admissionDate is less than UPDATED_ADMISSION_DATE
        defaultContributorShouldBeFound("admissionDate.lessThan=" + UPDATED_ADMISSION_DATE);
    }

    @Test
    @Transactional
    void getAllContributorsByAdmissionDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where admissionDate is greater than DEFAULT_ADMISSION_DATE
        defaultContributorShouldNotBeFound("admissionDate.greaterThan=" + DEFAULT_ADMISSION_DATE);

        // Get all the contributorList where admissionDate is greater than SMALLER_ADMISSION_DATE
        defaultContributorShouldBeFound("admissionDate.greaterThan=" + SMALLER_ADMISSION_DATE);
    }

    @Test
    @Transactional
    void getAllContributorsByCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where creationDate equals to DEFAULT_CREATION_DATE
        defaultContributorShouldBeFound("creationDate.equals=" + DEFAULT_CREATION_DATE);

        // Get all the contributorList where creationDate equals to UPDATED_CREATION_DATE
        defaultContributorShouldNotBeFound("creationDate.equals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    void getAllContributorsByCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where creationDate in DEFAULT_CREATION_DATE or UPDATED_CREATION_DATE
        defaultContributorShouldBeFound("creationDate.in=" + DEFAULT_CREATION_DATE + "," + UPDATED_CREATION_DATE);

        // Get all the contributorList where creationDate equals to UPDATED_CREATION_DATE
        defaultContributorShouldNotBeFound("creationDate.in=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    void getAllContributorsByCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where creationDate is not null
        defaultContributorShouldBeFound("creationDate.specified=true");

        // Get all the contributorList where creationDate is null
        defaultContributorShouldNotBeFound("creationDate.specified=false");
    }

    @Test
    @Transactional
    void getAllContributorsByCreationDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where creationDate is greater than or equal to DEFAULT_CREATION_DATE
        defaultContributorShouldBeFound("creationDate.greaterThanOrEqual=" + DEFAULT_CREATION_DATE);

        // Get all the contributorList where creationDate is greater than or equal to UPDATED_CREATION_DATE
        defaultContributorShouldNotBeFound("creationDate.greaterThanOrEqual=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    void getAllContributorsByCreationDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where creationDate is less than or equal to DEFAULT_CREATION_DATE
        defaultContributorShouldBeFound("creationDate.lessThanOrEqual=" + DEFAULT_CREATION_DATE);

        // Get all the contributorList where creationDate is less than or equal to SMALLER_CREATION_DATE
        defaultContributorShouldNotBeFound("creationDate.lessThanOrEqual=" + SMALLER_CREATION_DATE);
    }

    @Test
    @Transactional
    void getAllContributorsByCreationDateIsLessThanSomething() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where creationDate is less than DEFAULT_CREATION_DATE
        defaultContributorShouldNotBeFound("creationDate.lessThan=" + DEFAULT_CREATION_DATE);

        // Get all the contributorList where creationDate is less than UPDATED_CREATION_DATE
        defaultContributorShouldBeFound("creationDate.lessThan=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    void getAllContributorsByCreationDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where creationDate is greater than DEFAULT_CREATION_DATE
        defaultContributorShouldNotBeFound("creationDate.greaterThan=" + DEFAULT_CREATION_DATE);

        // Get all the contributorList where creationDate is greater than SMALLER_CREATION_DATE
        defaultContributorShouldBeFound("creationDate.greaterThan=" + SMALLER_CREATION_DATE);
    }

    @Test
    @Transactional
    void getAllContributorsByLastUpdateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where lastUpdateDate equals to DEFAULT_LAST_UPDATE_DATE
        defaultContributorShouldBeFound("lastUpdateDate.equals=" + DEFAULT_LAST_UPDATE_DATE);

        // Get all the contributorList where lastUpdateDate equals to UPDATED_LAST_UPDATE_DATE
        defaultContributorShouldNotBeFound("lastUpdateDate.equals=" + UPDATED_LAST_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllContributorsByLastUpdateDateIsInShouldWork() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where lastUpdateDate in DEFAULT_LAST_UPDATE_DATE or UPDATED_LAST_UPDATE_DATE
        defaultContributorShouldBeFound("lastUpdateDate.in=" + DEFAULT_LAST_UPDATE_DATE + "," + UPDATED_LAST_UPDATE_DATE);

        // Get all the contributorList where lastUpdateDate equals to UPDATED_LAST_UPDATE_DATE
        defaultContributorShouldNotBeFound("lastUpdateDate.in=" + UPDATED_LAST_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllContributorsByLastUpdateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where lastUpdateDate is not null
        defaultContributorShouldBeFound("lastUpdateDate.specified=true");

        // Get all the contributorList where lastUpdateDate is null
        defaultContributorShouldNotBeFound("lastUpdateDate.specified=false");
    }

    @Test
    @Transactional
    void getAllContributorsByLastUpdateDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where lastUpdateDate is greater than or equal to DEFAULT_LAST_UPDATE_DATE
        defaultContributorShouldBeFound("lastUpdateDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATE_DATE);

        // Get all the contributorList where lastUpdateDate is greater than or equal to UPDATED_LAST_UPDATE_DATE
        defaultContributorShouldNotBeFound("lastUpdateDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllContributorsByLastUpdateDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where lastUpdateDate is less than or equal to DEFAULT_LAST_UPDATE_DATE
        defaultContributorShouldBeFound("lastUpdateDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATE_DATE);

        // Get all the contributorList where lastUpdateDate is less than or equal to SMALLER_LAST_UPDATE_DATE
        defaultContributorShouldNotBeFound("lastUpdateDate.lessThanOrEqual=" + SMALLER_LAST_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllContributorsByLastUpdateDateIsLessThanSomething() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where lastUpdateDate is less than DEFAULT_LAST_UPDATE_DATE
        defaultContributorShouldNotBeFound("lastUpdateDate.lessThan=" + DEFAULT_LAST_UPDATE_DATE);

        // Get all the contributorList where lastUpdateDate is less than UPDATED_LAST_UPDATE_DATE
        defaultContributorShouldBeFound("lastUpdateDate.lessThan=" + UPDATED_LAST_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllContributorsByLastUpdateDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList where lastUpdateDate is greater than DEFAULT_LAST_UPDATE_DATE
        defaultContributorShouldNotBeFound("lastUpdateDate.greaterThan=" + DEFAULT_LAST_UPDATE_DATE);

        // Get all the contributorList where lastUpdateDate is greater than SMALLER_LAST_UPDATE_DATE
        defaultContributorShouldBeFound("lastUpdateDate.greaterThan=" + SMALLER_LAST_UPDATE_DATE);
    }

    @Test
    @Transactional
    void getAllContributorsBySeniorityIsEqualToSomething() throws Exception {
        Seniority seniority;
        if (TestUtil.findAll(em, Seniority.class).isEmpty()) {
            contributorRepository.saveAndFlush(contributor);
            seniority = SeniorityResourceIT.createEntity(em);
        } else {
            seniority = TestUtil.findAll(em, Seniority.class).get(0);
        }
        em.persist(seniority);
        em.flush();
        contributor.setSeniority(seniority);
        contributorRepository.saveAndFlush(contributor);
        Long seniorityId = seniority.getId();
        // Get all the contributorList where seniority equals to seniorityId
        defaultContributorShouldBeFound("seniorityId.equals=" + seniorityId);

        // Get all the contributorList where seniority equals to (seniorityId + 1)
        defaultContributorShouldNotBeFound("seniorityId.equals=" + (seniorityId + 1));
    }

    @Test
    @Transactional
    void getAllContributorsByCompetencesIsEqualToSomething() throws Exception {
        Competency competences;
        if (TestUtil.findAll(em, Competency.class).isEmpty()) {
            contributorRepository.saveAndFlush(contributor);
            competences = CompetencyResourceIT.createEntity(em);
        } else {
            competences = TestUtil.findAll(em, Competency.class).get(0);
        }
        em.persist(competences);
        em.flush();
        contributor.getCompetences().add(competences);
        contributorRepository.saveAndFlush(contributor);
        Long competencesId = competences.getId();
        // Get all the contributorList where competences equals to competencesId
        defaultContributorShouldBeFound("competencesId.equals=" + competencesId);

        // Get all the contributorList where competences equals to (competencesId + 1)
        defaultContributorShouldNotBeFound("competencesId.equals=" + (competencesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultContributorShouldBeFound(String filter) throws Exception {
        restContributorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contributor.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].admissionDate").value(hasItem(DEFAULT_ADMISSION_DATE.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(sameInstant(DEFAULT_CREATION_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdateDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATE_DATE))));

        // Check, that the count call also returns 1
        restContributorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultContributorShouldNotBeFound(String filter) throws Exception {
        restContributorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restContributorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingContributor() throws Exception {
        // Get the contributor
        restContributorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingContributor() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        int databaseSizeBeforeUpdate = contributorRepository.findAll().size();

        // Update the contributor
        Contributor updatedContributor = contributorRepository.findById(contributor.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedContributor are not directly saved in db
        em.detach(updatedContributor);
        updatedContributor.setName(UPDATED_NAME);
        updatedContributor.setLastName(UPDATED_LAST_NAME);
        updatedContributor.setCpf(UPDATED_CPF);
        updatedContributor.setEmail(UPDATED_EMAIL);
        updatedContributor.setPhoto(UPDATED_PHOTO);
        updatedContributor.setPhotoContentType(UPDATED_PHOTO_CONTENT_TYPE);
        updatedContributor.setBirthDate(UPDATED_BIRTH_DATE);
        updatedContributor.setAdmissionDate(UPDATED_ADMISSION_DATE);
        updatedContributor.setCreationDate(UPDATED_CREATION_DATE);
        updatedContributor.setLastUpdateDate(UPDATED_LAST_UPDATE_DATE);
        ContributorDTO contributorDTO = contributorMapper.toDto(updatedContributor);

        restContributorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contributorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contributorDTO))
            )
            .andExpect(status().isOk());

        // Validate the Contributor in the database
        List<Contributor> contributorList = contributorRepository.findAll();
        assertThat(contributorList).hasSize(databaseSizeBeforeUpdate);
        Contributor testContributor = contributorList.get(contributorList.size() - 1);
        assertThat(testContributor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testContributor.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testContributor.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testContributor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testContributor.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testContributor.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testContributor.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testContributor.getAdmissionDate()).isEqualTo(UPDATED_ADMISSION_DATE);
        assertThat(testContributor.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testContributor.getLastUpdateDate()).isEqualTo(UPDATED_LAST_UPDATE_DATE);
    }

    @Test
    @Transactional
    void putNonExistingContributor() throws Exception {
        int databaseSizeBeforeUpdate = contributorRepository.findAll().size();
        contributor.setId(count.incrementAndGet());

        // Create the Contributor
        ContributorDTO contributorDTO = contributorMapper.toDto(contributor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContributorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contributorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contributorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contributor in the database
        List<Contributor> contributorList = contributorRepository.findAll();
        assertThat(contributorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContributor() throws Exception {
        int databaseSizeBeforeUpdate = contributorRepository.findAll().size();
        contributor.setId(count.incrementAndGet());

        // Create the Contributor
        ContributorDTO contributorDTO = contributorMapper.toDto(contributor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContributorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contributorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contributor in the database
        List<Contributor> contributorList = contributorRepository.findAll();
        assertThat(contributorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContributor() throws Exception {
        int databaseSizeBeforeUpdate = contributorRepository.findAll().size();
        contributor.setId(count.incrementAndGet());

        // Create the Contributor
        ContributorDTO contributorDTO = contributorMapper.toDto(contributor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContributorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contributorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contributor in the database
        List<Contributor> contributorList = contributorRepository.findAll();
        assertThat(contributorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContributorWithPatch() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        int databaseSizeBeforeUpdate = contributorRepository.findAll().size();

        // Update the contributor using partial update

        Contributor partialUpdatedContributor = Contributor.builder()
            .id(contributor.getId())
            .cpf(UPDATED_CPF)
            .email(UPDATED_EMAIL)
            .admissionDate(UPDATED_ADMISSION_DATE)
            .lastUpdateDate(UPDATED_LAST_UPDATE_DATE).build();

        restContributorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContributor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContributor))
            )
            .andExpect(status().isOk());

        // Validate the Contributor in the database
        List<Contributor> contributorList = contributorRepository.findAll();
        assertThat(contributorList).hasSize(databaseSizeBeforeUpdate);
        Contributor testContributor = contributorList.get(contributorList.size() - 1);
        assertThat(testContributor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testContributor.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testContributor.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testContributor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testContributor.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testContributor.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testContributor.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testContributor.getAdmissionDate()).isEqualTo(UPDATED_ADMISSION_DATE);
        assertThat(testContributor.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testContributor.getLastUpdateDate()).isEqualTo(UPDATED_LAST_UPDATE_DATE);
    }

    @Test
    @Transactional
    void fullUpdateContributorWithPatch() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        int databaseSizeBeforeUpdate = contributorRepository.findAll().size();

        // Update the contributor using partial update
        Contributor partialUpdatedContributor = Contributor.builder()
            .id(contributor.getId())
            .name(UPDATED_NAME)
            .lastName(UPDATED_LAST_NAME)
            .cpf(UPDATED_CPF)
            .email(UPDATED_EMAIL)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .birthDate(UPDATED_BIRTH_DATE)
            .admissionDate(UPDATED_ADMISSION_DATE)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdateDate(UPDATED_LAST_UPDATE_DATE).build();

        restContributorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContributor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContributor))
            )
            .andExpect(status().isOk());

        // Validate the Contributor in the database
        List<Contributor> contributorList = contributorRepository.findAll();
        assertThat(contributorList).hasSize(databaseSizeBeforeUpdate);
        Contributor testContributor = contributorList.get(contributorList.size() - 1);
        assertThat(testContributor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testContributor.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testContributor.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testContributor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testContributor.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testContributor.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testContributor.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testContributor.getAdmissionDate()).isEqualTo(UPDATED_ADMISSION_DATE);
        assertThat(testContributor.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testContributor.getLastUpdateDate()).isEqualTo(UPDATED_LAST_UPDATE_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingContributor() throws Exception {
        int databaseSizeBeforeUpdate = contributorRepository.findAll().size();
        contributor.setId(count.incrementAndGet());

        // Create the Contributor
        ContributorDTO contributorDTO = contributorMapper.toDto(contributor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContributorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contributorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contributorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contributor in the database
        List<Contributor> contributorList = contributorRepository.findAll();
        assertThat(contributorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContributor() throws Exception {
        int databaseSizeBeforeUpdate = contributorRepository.findAll().size();
        contributor.setId(count.incrementAndGet());

        // Create the Contributor
        ContributorDTO contributorDTO = contributorMapper.toDto(contributor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContributorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contributorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contributor in the database
        List<Contributor> contributorList = contributorRepository.findAll();
        assertThat(contributorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContributor() throws Exception {
        int databaseSizeBeforeUpdate = contributorRepository.findAll().size();
        contributor.setId(count.incrementAndGet());

        // Create the Contributor
        ContributorDTO contributorDTO = contributorMapper.toDto(contributor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContributorMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(contributorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contributor in the database
        List<Contributor> contributorList = contributorRepository.findAll();
        assertThat(contributorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContributor() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        int databaseSizeBeforeDelete = contributorRepository.findAll().size();

        // Delete the contributor
        restContributorMockMvc
            .perform(delete(ENTITY_API_URL_ID, contributor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Contributor> contributorList = contributorRepository.findAll();
        assertThat(contributorList).hasSize(databaseSizeBeforeDelete - 1);
    }

}

package com.trandung.elearn.web.rest;

import com.trandung.elearn.ElearnApp;
import com.trandung.elearn.domain.Session;
import com.trandung.elearn.repository.SessionRepository;
import com.trandung.elearn.service.SessionService;
import com.trandung.elearn.service.dto.SessionDTO;
import com.trandung.elearn.service.mapper.SessionMapper;
import com.trandung.elearn.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.trandung.elearn.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SessionResource} REST controller.
 */
@SpringBootTest(classes = ElearnApp.class)
public class SessionResourceIT {

    private static final Integer DEFAULT_SESSION_NUMBER = 1;
    private static final Integer UPDATED_SESSION_NUMBER = 2;
    private static final Integer SMALLER_SESSION_NUMBER = 1 - 1;

    private static final Integer DEFAULT_SCORES = 1;
    private static final Integer UPDATED_SCORES = 2;
    private static final Integer SMALLER_SCORES = 1 - 1;

    private static final Integer DEFAULT_WRONG_ANSWER = 1;
    private static final Integer UPDATED_WRONG_ANSWER = 2;
    private static final Integer SMALLER_WRONG_ANSWER = 1 - 1;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private SessionMapper sessionMapper;

    @Autowired
    private SessionService sessionService;

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

    private MockMvc restSessionMockMvc;

    private Session session;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SessionResource sessionResource = new SessionResource(sessionService);
        this.restSessionMockMvc = MockMvcBuilders.standaloneSetup(sessionResource)
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
    public static Session createEntity(EntityManager em) {
        Session session = new Session()
            .sessionNumber(DEFAULT_SESSION_NUMBER)
            .scores(DEFAULT_SCORES)
            .wrongAnswer(DEFAULT_WRONG_ANSWER);
        return session;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Session createUpdatedEntity(EntityManager em) {
        Session session = new Session()
            .sessionNumber(UPDATED_SESSION_NUMBER)
            .scores(UPDATED_SCORES)
            .wrongAnswer(UPDATED_WRONG_ANSWER);
        return session;
    }

    @BeforeEach
    public void initTest() {
        session = createEntity(em);
    }

    @Test
    @Transactional
    public void createSession() throws Exception {
        int databaseSizeBeforeCreate = sessionRepository.findAll().size();

        // Create the Session
        SessionDTO sessionDTO = sessionMapper.toDto(session);
        restSessionMockMvc.perform(post("/api/sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sessionDTO)))
            .andExpect(status().isCreated());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeCreate + 1);
        Session testSession = sessionList.get(sessionList.size() - 1);
        assertThat(testSession.getSessionNumber()).isEqualTo(DEFAULT_SESSION_NUMBER);
        assertThat(testSession.getScores()).isEqualTo(DEFAULT_SCORES);
        assertThat(testSession.getWrongAnswer()).isEqualTo(DEFAULT_WRONG_ANSWER);
    }

    @Test
    @Transactional
    public void createSessionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sessionRepository.findAll().size();

        // Create the Session with an existing ID
        session.setId(1L);
        SessionDTO sessionDTO = sessionMapper.toDto(session);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSessionMockMvc.perform(post("/api/sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sessionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSessions() throws Exception {
        // Initialize the database
        sessionRepository.saveAndFlush(session);

        // Get all the sessionList
        restSessionMockMvc.perform(get("/api/sessions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(session.getId().intValue())))
            .andExpect(jsonPath("$.[*].sessionNumber").value(hasItem(DEFAULT_SESSION_NUMBER)))
            .andExpect(jsonPath("$.[*].scores").value(hasItem(DEFAULT_SCORES)))
            .andExpect(jsonPath("$.[*].wrongAnswer").value(hasItem(DEFAULT_WRONG_ANSWER)));
    }
    
    @Test
    @Transactional
    public void getSession() throws Exception {
        // Initialize the database
        sessionRepository.saveAndFlush(session);

        // Get the session
        restSessionMockMvc.perform(get("/api/sessions/{id}", session.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(session.getId().intValue()))
            .andExpect(jsonPath("$.sessionNumber").value(DEFAULT_SESSION_NUMBER))
            .andExpect(jsonPath("$.scores").value(DEFAULT_SCORES))
            .andExpect(jsonPath("$.wrongAnswer").value(DEFAULT_WRONG_ANSWER));
    }

    @Test
    @Transactional
    public void getNonExistingSession() throws Exception {
        // Get the session
        restSessionMockMvc.perform(get("/api/sessions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSession() throws Exception {
        // Initialize the database
        sessionRepository.saveAndFlush(session);

        int databaseSizeBeforeUpdate = sessionRepository.findAll().size();

        // Update the session
        Session updatedSession = sessionRepository.findById(session.getId()).get();
        // Disconnect from session so that the updates on updatedSession are not directly saved in db
        em.detach(updatedSession);
        updatedSession
            .sessionNumber(UPDATED_SESSION_NUMBER)
            .scores(UPDATED_SCORES)
            .wrongAnswer(UPDATED_WRONG_ANSWER);
        SessionDTO sessionDTO = sessionMapper.toDto(updatedSession);

        restSessionMockMvc.perform(put("/api/sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sessionDTO)))
            .andExpect(status().isOk());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeUpdate);
        Session testSession = sessionList.get(sessionList.size() - 1);
        assertThat(testSession.getSessionNumber()).isEqualTo(UPDATED_SESSION_NUMBER);
        assertThat(testSession.getScores()).isEqualTo(UPDATED_SCORES);
        assertThat(testSession.getWrongAnswer()).isEqualTo(UPDATED_WRONG_ANSWER);
    }

    @Test
    @Transactional
    public void updateNonExistingSession() throws Exception {
        int databaseSizeBeforeUpdate = sessionRepository.findAll().size();

        // Create the Session
        SessionDTO sessionDTO = sessionMapper.toDto(session);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSessionMockMvc.perform(put("/api/sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sessionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSession() throws Exception {
        // Initialize the database
        sessionRepository.saveAndFlush(session);

        int databaseSizeBeforeDelete = sessionRepository.findAll().size();

        // Delete the session
        restSessionMockMvc.perform(delete("/api/sessions/{id}", session.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Session.class);
        Session session1 = new Session();
        session1.setId(1L);
        Session session2 = new Session();
        session2.setId(session1.getId());
        assertThat(session1).isEqualTo(session2);
        session2.setId(2L);
        assertThat(session1).isNotEqualTo(session2);
        session1.setId(null);
        assertThat(session1).isNotEqualTo(session2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SessionDTO.class);
        SessionDTO sessionDTO1 = new SessionDTO();
        sessionDTO1.setId(1L);
        SessionDTO sessionDTO2 = new SessionDTO();
        assertThat(sessionDTO1).isNotEqualTo(sessionDTO2);
        sessionDTO2.setId(sessionDTO1.getId());
        assertThat(sessionDTO1).isEqualTo(sessionDTO2);
        sessionDTO2.setId(2L);
        assertThat(sessionDTO1).isNotEqualTo(sessionDTO2);
        sessionDTO1.setId(null);
        assertThat(sessionDTO1).isNotEqualTo(sessionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sessionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sessionMapper.fromId(null)).isNull();
    }
}

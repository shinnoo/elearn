package com.trandung.elearn.web.rest;

import com.trandung.elearn.ElearnApp;
import com.trandung.elearn.domain.Word;
import com.trandung.elearn.repository.WordRepository;
import com.trandung.elearn.service.WordService;
import com.trandung.elearn.service.dto.WordDTO;
import com.trandung.elearn.service.mapper.WordMapper;
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
 * Integration tests for the {@link WordResource} REST controller.
 */
@SpringBootTest(classes = ElearnApp.class)
public class WordResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DEFINITION = "AAAAAAAAAA";
    private static final String UPDATED_DEFINITION = "BBBBBBBBBB";

    private static final String DEFAULT_PRONOUNCE = "AAAAAAAAAA";
    private static final String UPDATED_PRONOUNCE = "BBBBBBBBBB";

    private static final String DEFAULT_FORM = "AAAAAAAAAA";
    private static final String UPDATED_FORM = "BBBBBBBBBB";

    private static final String DEFAULT_EXAMPLE = "AAAAAAAAAA";
    private static final String UPDATED_EXAMPLE = "BBBBBBBBBB";

    private static final String DEFAULT_COLLOCATIONS = "AAAAAAAAAA";
    private static final String UPDATED_COLLOCATIONS = "BBBBBBBBBB";

    private static final String DEFAULT_SYNONYM = "AAAAAAAAAA";
    private static final String UPDATED_SYNONYM = "BBBBBBBBBB";

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private WordMapper wordMapper;

    @Autowired
    private WordService wordService;

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

    private MockMvc restWordMockMvc;

    private Word word;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WordResource wordResource = new WordResource(wordService);
        this.restWordMockMvc = MockMvcBuilders.standaloneSetup(wordResource)
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
    public static Word createEntity(EntityManager em) {
        Word word = new Word()
            .name(DEFAULT_NAME)
            .definition(DEFAULT_DEFINITION)
            .pronounce(DEFAULT_PRONOUNCE)
            .form(DEFAULT_FORM)
            .example(DEFAULT_EXAMPLE)
            .collocations(DEFAULT_COLLOCATIONS)
            .synonym(DEFAULT_SYNONYM);
        return word;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Word createUpdatedEntity(EntityManager em) {
        Word word = new Word()
            .name(UPDATED_NAME)
            .definition(UPDATED_DEFINITION)
            .pronounce(UPDATED_PRONOUNCE)
            .form(UPDATED_FORM)
            .example(UPDATED_EXAMPLE)
            .collocations(UPDATED_COLLOCATIONS)
            .synonym(UPDATED_SYNONYM);
        return word;
    }

    @BeforeEach
    public void initTest() {
        word = createEntity(em);
    }

    @Test
    @Transactional
    public void createWord() throws Exception {
        int databaseSizeBeforeCreate = wordRepository.findAll().size();

        // Create the Word
        WordDTO wordDTO = wordMapper.toDto(word);
        restWordMockMvc.perform(post("/api/words")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wordDTO)))
            .andExpect(status().isCreated());

        // Validate the Word in the database
        List<Word> wordList = wordRepository.findAll();
        assertThat(wordList).hasSize(databaseSizeBeforeCreate + 1);
        Word testWord = wordList.get(wordList.size() - 1);
        assertThat(testWord.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWord.getDefinition()).isEqualTo(DEFAULT_DEFINITION);
        assertThat(testWord.getPronounce()).isEqualTo(DEFAULT_PRONOUNCE);
        assertThat(testWord.getForm()).isEqualTo(DEFAULT_FORM);
        assertThat(testWord.getExample()).isEqualTo(DEFAULT_EXAMPLE);
        assertThat(testWord.getCollocations()).isEqualTo(DEFAULT_COLLOCATIONS);
        assertThat(testWord.getSynonym()).isEqualTo(DEFAULT_SYNONYM);
    }

    @Test
    @Transactional
    public void createWordWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wordRepository.findAll().size();

        // Create the Word with an existing ID
        word.setId(1L);
        WordDTO wordDTO = wordMapper.toDto(word);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWordMockMvc.perform(post("/api/words")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wordDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Word in the database
        List<Word> wordList = wordRepository.findAll();
        assertThat(wordList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWords() throws Exception {
        // Initialize the database
        wordRepository.saveAndFlush(word);

        // Get all the wordList
        restWordMockMvc.perform(get("/api/words?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(word.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].definition").value(hasItem(DEFAULT_DEFINITION.toString())))
            .andExpect(jsonPath("$.[*].pronounce").value(hasItem(DEFAULT_PRONOUNCE.toString())))
            .andExpect(jsonPath("$.[*].form").value(hasItem(DEFAULT_FORM.toString())))
            .andExpect(jsonPath("$.[*].example").value(hasItem(DEFAULT_EXAMPLE.toString())))
            .andExpect(jsonPath("$.[*].collocations").value(hasItem(DEFAULT_COLLOCATIONS.toString())))
            .andExpect(jsonPath("$.[*].synonym").value(hasItem(DEFAULT_SYNONYM.toString())));
    }
    
    @Test
    @Transactional
    public void getWord() throws Exception {
        // Initialize the database
        wordRepository.saveAndFlush(word);

        // Get the word
        restWordMockMvc.perform(get("/api/words/{id}", word.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(word.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.definition").value(DEFAULT_DEFINITION.toString()))
            .andExpect(jsonPath("$.pronounce").value(DEFAULT_PRONOUNCE.toString()))
            .andExpect(jsonPath("$.form").value(DEFAULT_FORM.toString()))
            .andExpect(jsonPath("$.example").value(DEFAULT_EXAMPLE.toString()))
            .andExpect(jsonPath("$.collocations").value(DEFAULT_COLLOCATIONS.toString()))
            .andExpect(jsonPath("$.synonym").value(DEFAULT_SYNONYM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWord() throws Exception {
        // Get the word
        restWordMockMvc.perform(get("/api/words/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWord() throws Exception {
        // Initialize the database
        wordRepository.saveAndFlush(word);

        int databaseSizeBeforeUpdate = wordRepository.findAll().size();

        // Update the word
        Word updatedWord = wordRepository.findById(word.getId()).get();
        // Disconnect from session so that the updates on updatedWord are not directly saved in db
        em.detach(updatedWord);
        updatedWord
            .name(UPDATED_NAME)
            .definition(UPDATED_DEFINITION)
            .pronounce(UPDATED_PRONOUNCE)
            .form(UPDATED_FORM)
            .example(UPDATED_EXAMPLE)
            .collocations(UPDATED_COLLOCATIONS)
            .synonym(UPDATED_SYNONYM);
        WordDTO wordDTO = wordMapper.toDto(updatedWord);

        restWordMockMvc.perform(put("/api/words")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wordDTO)))
            .andExpect(status().isOk());

        // Validate the Word in the database
        List<Word> wordList = wordRepository.findAll();
        assertThat(wordList).hasSize(databaseSizeBeforeUpdate);
        Word testWord = wordList.get(wordList.size() - 1);
        assertThat(testWord.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWord.getDefinition()).isEqualTo(UPDATED_DEFINITION);
        assertThat(testWord.getPronounce()).isEqualTo(UPDATED_PRONOUNCE);
        assertThat(testWord.getForm()).isEqualTo(UPDATED_FORM);
        assertThat(testWord.getExample()).isEqualTo(UPDATED_EXAMPLE);
        assertThat(testWord.getCollocations()).isEqualTo(UPDATED_COLLOCATIONS);
        assertThat(testWord.getSynonym()).isEqualTo(UPDATED_SYNONYM);
    }

    @Test
    @Transactional
    public void updateNonExistingWord() throws Exception {
        int databaseSizeBeforeUpdate = wordRepository.findAll().size();

        // Create the Word
        WordDTO wordDTO = wordMapper.toDto(word);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWordMockMvc.perform(put("/api/words")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wordDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Word in the database
        List<Word> wordList = wordRepository.findAll();
        assertThat(wordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWord() throws Exception {
        // Initialize the database
        wordRepository.saveAndFlush(word);

        int databaseSizeBeforeDelete = wordRepository.findAll().size();

        // Delete the word
        restWordMockMvc.perform(delete("/api/words/{id}", word.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Word> wordList = wordRepository.findAll();
        assertThat(wordList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Word.class);
        Word word1 = new Word();
        word1.setId(1L);
        Word word2 = new Word();
        word2.setId(word1.getId());
        assertThat(word1).isEqualTo(word2);
        word2.setId(2L);
        assertThat(word1).isNotEqualTo(word2);
        word1.setId(null);
        assertThat(word1).isNotEqualTo(word2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WordDTO.class);
        WordDTO wordDTO1 = new WordDTO();
        wordDTO1.setId(1L);
        WordDTO wordDTO2 = new WordDTO();
        assertThat(wordDTO1).isNotEqualTo(wordDTO2);
        wordDTO2.setId(wordDTO1.getId());
        assertThat(wordDTO1).isEqualTo(wordDTO2);
        wordDTO2.setId(2L);
        assertThat(wordDTO1).isNotEqualTo(wordDTO2);
        wordDTO1.setId(null);
        assertThat(wordDTO1).isNotEqualTo(wordDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(wordMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(wordMapper.fromId(null)).isNull();
    }
}

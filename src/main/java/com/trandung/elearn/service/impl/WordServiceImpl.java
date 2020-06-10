package com.trandung.elearn.service.impl;

import com.trandung.elearn.service.WordService;
import com.trandung.elearn.config.Constants;
import com.trandung.elearn.domain.Word;
import com.trandung.elearn.repository.WordRepository;
import com.trandung.elearn.service.dto.WordDTO;
import com.trandung.elearn.service.mapper.WordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Word}.
 */
@Service
@Transactional
public class WordServiceImpl implements WordService {

    private final Logger log = LoggerFactory.getLogger(WordServiceImpl.class);

    private final WordRepository wordRepository;

    private final WordMapper wordMapper;

    public WordServiceImpl(WordRepository wordRepository, WordMapper wordMapper) {
        this.wordRepository = wordRepository;
        this.wordMapper = wordMapper;
    }

    /**
     * Save a word.
     *
     * @param wordDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public WordDTO save(WordDTO wordDTO) {
        log.debug("Request to save Word : {}", wordDTO);
        Word word = wordMapper.toEntity(wordDTO);
        word = wordRepository.save(word);
        return wordMapper.toDto(word);
    }

    /**
     * Get all the words.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WordDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Words");
        return wordRepository.findAll(pageable)
            .map(wordMapper::toDto);
    }


    /**
     * Get one word by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WordDTO> findOne(Long id) {
        log.debug("Request to get Word : {}", id);
        return wordRepository.findById(id)
            .map(wordMapper::toDto);
    }

    /**
     * Delete the word by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Word : {}", id);
        wordRepository.deleteById(id);
    }

    @Override
    public WordDTO updateStatus(Long id, Integer status) {
        Optional<Word> wordOptional = wordRepository.findById(id);
        if (wordOptional.isPresent()) {
            Word word = wordOptional.get();
            if (!Constants.ENTITY_STATUS.DELETED.equals(word.getStatus())) {
                word.setStatus(status);
                wordRepository.save(word);
                return wordMapper.toDto(word);
            }
        }
        return null;
    }

    @Override
    public Page<WordDTO> findAllByCreatedBy(Pageable pageable, String createdBy) {
        log.debug("Request to get all Words by CreatedBy");
        return wordRepository.findAllByCreatedBy(pageable, createdBy)
            .map(wordMapper::toDto);
    }

}

package com.trandung.elearn.repository;

import java.util.List;

import com.trandung.elearn.domain.Word;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Word entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
    Page<Word> findAllByCreatedBy(Pageable pageable, String createdBy);
}

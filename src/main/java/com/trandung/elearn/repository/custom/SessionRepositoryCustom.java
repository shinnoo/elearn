package com.trandung.elearn.repository.custom;

import java.util.List;

import com.trandung.elearn.domain.Session;

import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

public interface SessionRepositoryCustom {
    List<Session> search(MultiValueMap<String, String> queryParams, Pageable pageable);

    Long count(MultiValueMap<String, String> queryParams);
}
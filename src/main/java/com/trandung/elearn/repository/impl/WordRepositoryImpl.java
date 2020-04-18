package com.trandung.elearn.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.trandung.elearn.domain.Word;
import com.trandung.elearn.repository.custom.WordRepositoryCustom;

import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

public class WordRepositoryImpl implements WordRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public List<Word> search(MultiValueMap<String, String> queryParams, Pageable pageable) {
        String sql = "select C from AccountBalance C where C.status <> 0 ";
        Map<String, Object> values = new HashMap<>();
        sql += createWhereQuery(queryParams, values);
        sql += createOrderQuery(queryParams);
        Query query = entityManager.createQuery(sql, Word.class);
        values.forEach(query::setParameter);
        if (pageable != null) {
            query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
            query.setMaxResults(pageable.getPageSize());
        }
        return query.getResultList();
    }

    @Override
    public Long count(MultiValueMap<String, String> queryParams) {
        String sql = "select count(W) from Word C where C.status <> 0";
        Map<String, Object> values = new HashMap<>();
        sql += createWhereQuery(queryParams, values);
        Query query = entityManager.createQuery(sql, Long.class);
        values.forEach(query::setParameter);
        return (Long) query.getSingleResult();
    }

    private String createWhereQuery(MultiValueMap<String, String> queryParams, Map<String, Object> values) {
        String sql = "";
        // if (queryParams.containsKey("unitName")) {
        //     sql += " and lower(A.unitName) like lower(:unitName)";
        //     values.put("unitName", "%" + queryParams.get("unitName").get(0) + "%");
        // }
        // if (queryParams.containsKey("unitCode")) {
        //     sql += " and A.unitCode like :unitCode";
        //     values.put("unitCode", "%" + queryParams.get("unitCode").get(0) + "%");
        // }
        // if (queryParams.containsKey("postalCode")) {
        //     sql += " and A.postalCode like :postalCode";
        //     values.put("postalCode", "%" + queryParams.get("postalCode").get(0) + "%");
        // }
        return sql;
    }

    private String createOrderQuery(MultiValueMap<String, String> queryParams) {
        String sql = " order by ";
        if (queryParams.containsKey("sort")) {
            List<String> orderByList = queryParams.get("sort");
            for (String i : orderByList) {
                sql += "W." + i.replace(",", " ") + ", ";
            }
            sql = sql.substring(0, sql.lastIndexOf(","));
        } else {
            sql += " W.createdDate desc ";
        }
        return sql;
    }

}
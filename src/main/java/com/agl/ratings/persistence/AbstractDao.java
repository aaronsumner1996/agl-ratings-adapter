package com.agl.ratings.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@PersistenceContext
public class AbstractDao {

    @PersistenceContext
    protected EntityManager entityManager;


    protected <T> List<T> findAll(String qlQuery, Class<T> resultClass, Object... params) throws Exception {
        Query query = setQueryParameters(entityManager.createQuery(qlQuery, resultClass), params);
        return query.getResultList();
    }

    protected <T> T findOne(String qlQuery, Class<T> resultClass, Object... params) throws Exception {
        Query query = setQueryParameters(entityManager.createQuery(qlQuery, resultClass), params);
        List<T> list = query.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    private Query setQueryParameters(Query query, Object... params) throws Exception {
        if (params == null || params.length % 2 != 0) {
            throw new Exception("Please specify params in multiples of 2 i.e. key/value pair");
        }

        for (int i = 0; i < params.length; i += 2) {
            query.setParameter((String) params[i], params[i + 1]);

        }
        return query;
    }

}

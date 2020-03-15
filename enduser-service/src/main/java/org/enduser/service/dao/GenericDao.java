package org.enduser.service.dao;

import java.util.List;

public interface GenericDao<T> {

    T save(T obj);

    T update(T obj);

    // T findOne(ID id);
    List<T> findAll();

    void delete(T obj);
}

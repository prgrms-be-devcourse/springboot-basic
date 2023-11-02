package com.programmers.springbootbasic.common;

import java.util.List;
import java.util.Optional;

public interface Repository<ID, T> {

    T save(T entity);

    Optional<T> findById(ID id);

    List<T> findAll();

    int deleteById(ID id);

    int update(T entity);
}

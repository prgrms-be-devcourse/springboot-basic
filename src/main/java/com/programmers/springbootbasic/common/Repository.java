package com.programmers.springbootbasic.common;

import java.util.List;
import java.util.Optional;

public interface Repository<ID,T> {

    <S extends T> S save(S entity);

    Optional<T> findById(ID id);

    List<T> findAll();

    int deleteById(ID id);

    <S extends T> int update(S entity);
}

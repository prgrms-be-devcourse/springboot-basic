package org.prgrms.deukyun.voucherapp.domain.common.repository;

import java.util.List;
import java.util.Optional;

/**
 * 리포지토리
 * @param <E> 저장 타입
 * @param <Id> 아이디 타입
 */
public interface Repository<E, Id> {

    E insert(E elem);

    Optional<E> findById(Id id);

    List<E> findAll();
}

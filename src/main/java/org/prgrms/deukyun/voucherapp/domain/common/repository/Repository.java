package org.prgrms.deukyun.voucherapp.domain.common.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<E, Id> {

    E insert(E elem);

    Optional<E> findById(Id id);

    List<E> findAll();
}

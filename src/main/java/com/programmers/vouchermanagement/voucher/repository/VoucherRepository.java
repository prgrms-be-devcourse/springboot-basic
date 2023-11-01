package com.programmers.vouchermanagement.voucher.repository;

import com.programmers.vouchermanagement.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    void save(Voucher voucher);

    List<Voucher> findAll();

    Optional<Voucher> findById(UUID id);

    void delete(UUID id);

    void deleteAll();

    void update(Voucher voucher);
}

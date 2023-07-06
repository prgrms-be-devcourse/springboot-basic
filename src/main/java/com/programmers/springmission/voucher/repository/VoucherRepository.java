package com.programmers.springmission.voucher.repository;

import com.programmers.springmission.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    void save(Voucher voucher);

    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findAll();

    void update(Voucher voucher);

    void deleteById(UUID voucherId);

    void deleteAll();
}


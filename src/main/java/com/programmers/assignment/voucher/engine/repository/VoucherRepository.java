package com.programmers.assignment.voucher.engine.repository;

import com.programmers.assignment.voucher.engine.voucher.Voucher;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);

    Map<UUID, Voucher> findAll();

    void insert(Voucher voucher);

    void save(Map<UUID, Voucher> map);
}

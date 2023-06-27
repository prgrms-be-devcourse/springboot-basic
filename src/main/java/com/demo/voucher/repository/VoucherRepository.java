package com.demo.voucher.repository;

import com.demo.voucher.domain.Voucher;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);

    Map<UUID, Voucher> findAll();

    Voucher insert(Voucher voucher);

    void clear();
}

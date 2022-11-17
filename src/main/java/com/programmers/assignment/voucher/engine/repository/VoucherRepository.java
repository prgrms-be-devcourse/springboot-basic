package com.programmers.assignment.voucher.engine.repository;

import com.programmers.assignment.voucher.engine.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findAll();

    void insert(Voucher voucher);
}

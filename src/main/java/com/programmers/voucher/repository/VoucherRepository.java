package com.programmers.voucher.repository;

import com.programmers.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    void save(Voucher voucher);

    List<Voucher> findAll();

    Optional<Voucher> findByVoucherId(UUID voucherId);

    void clear();
}

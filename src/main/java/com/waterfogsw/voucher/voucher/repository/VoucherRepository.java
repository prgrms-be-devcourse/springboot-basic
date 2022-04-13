package com.waterfogsw.voucher.voucher.repository;

import com.waterfogsw.voucher.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    void save(Voucher voucher);

    Optional<Voucher> findById(UUID id);

    List<Voucher> findAll();
}

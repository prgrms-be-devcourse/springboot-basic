package com.waterfogsw.voucher.voucher.repository;

import com.waterfogsw.voucher.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;

public interface VoucherRepository {
    Voucher save(Voucher voucher);

    List<Voucher> findAll();

    Optional<Voucher> findById(Long id);
}

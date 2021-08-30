package com.programmers.kdtspringorder.voucher.repository;

import com.programmers.kdtspringorder.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findAll();

    Voucher save(Voucher voucher);

    void delete(UUID voucherId);
}

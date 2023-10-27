package com.programmers.vouchermanagement.voucher.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.programmers.vouchermanagement.voucher.domain.Voucher;

public interface VoucherRepository {
    Voucher save(Voucher voucher);
    List<Voucher> findAll();
    Optional<Voucher> findById(UUID voucherId);
    void deleteById(UUID voucherId);
    void deleteAll();
    default boolean existById(UUID voucherId) {
        return findById(voucherId).isPresent();
    };
}

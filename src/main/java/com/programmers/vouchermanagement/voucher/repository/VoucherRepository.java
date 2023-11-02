package com.programmers.vouchermanagement.voucher.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;

public interface VoucherRepository {
    Voucher save(Voucher voucher);
    List<Voucher> findAll();
    List<Voucher> findByType(VoucherType voucherType);
    Optional<Voucher> findById(UUID voucherId);
    List<Voucher> findByCustomerId(UUID customerId);
    void deleteById(UUID voucherId);
    void deleteAll();
    default boolean existById(UUID voucherId) {
        return findById(voucherId).isPresent();
    };
}

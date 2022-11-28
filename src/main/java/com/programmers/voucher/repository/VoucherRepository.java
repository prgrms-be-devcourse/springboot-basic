package com.programmers.voucher.repository;

import com.programmers.voucher.domain.Voucher;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    void insert(Voucher voucher);
    List<Voucher> findAll();
    List<Voucher> findByCustomerEmail(String customerEmail);
    List<Voucher> findByDate(String customerEmail, LocalDateTime date);
    Optional<Voucher> findByVoucherId(UUID voucherId);
    void update(UUID voucherId, long discount);
    void deleteByVoucherId(UUID voucherId);
    void deleteAll();
}

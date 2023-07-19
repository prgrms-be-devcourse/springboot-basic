package com.tangerine.voucher_system.application.voucher.repository;

import com.tangerine.voucher_system.application.voucher.model.Voucher;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Voucher insert(Voucher voucher);

    Voucher update(Voucher voucher);

    List<Voucher> findAll();

    Optional<Voucher> findById(UUID voucherId);

    Optional<Voucher> findByCreatedAt(LocalDate createdAt);

    void deleteById(UUID voucherId);

}

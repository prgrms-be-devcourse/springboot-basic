package com.tangerine.voucher_system.application.voucher.repository;

import com.tangerine.voucher_system.application.voucher.model.Voucher;
import com.tangerine.voucher_system.application.voucher.model.VoucherType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Voucher insert(Voucher voucher);

    Voucher update(Voucher voucher);

    List<Voucher> findAll();

    Optional<Voucher> findById(UUID voucherId);

    Optional<Voucher> findByVoucherType(VoucherType voucherType);

    Optional<Voucher> findByCreatedAt(LocalDateTime createdAt);

    void deleteById(UUID voucherId);

}

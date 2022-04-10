package com.prgrms.management.voucher.repository;

import com.prgrms.management.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
    Voucher insert(Voucher voucher);

    List<Voucher> findAll();
}

package com.prgrm.kdt.voucher.repository;

import com.prgrm.kdt.voucher.domain.Voucher;

import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
}

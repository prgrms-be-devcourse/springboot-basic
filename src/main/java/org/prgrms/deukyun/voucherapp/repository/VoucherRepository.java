package org.prgrms.deukyun.voucherapp.repository;

import org.prgrms.deukyun.voucherapp.entity.Voucher;

import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
}

package org.prgrms.deukyun.voucherapp.voucher.repository;

import org.prgrms.deukyun.voucherapp.voucher.entity.Voucher;

import java.util.Optional;
import java.util.UUID;

/**
 * 바우처 리포지토리
 */
public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);

    Voucher insert(Voucher voucher);
}

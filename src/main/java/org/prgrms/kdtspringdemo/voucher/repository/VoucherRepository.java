package org.prgrms.kdtspringdemo.voucher.repository;

import org.prgrms.kdtspringdemo.voucher.domain.Voucher;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Voucher insert(Voucher voucher);
    Optional<Voucher> findById(UUID voucherId);
    Optional<Map<UUID, Voucher>> getAllVouchers();
}

package org.prgrms.kdt.engine.voucher.repository;

import org.prgrms.kdt.engine.voucher.domain.Voucher;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
    Optional<Map<UUID, Voucher>> getAll();
    Voucher insert(Voucher voucher);
    void setCustomerId(UUID voucherId, UUID customerId);
    Optional<Map<UUID, Voucher>> getCustomerVoucher(UUID customerId);
}

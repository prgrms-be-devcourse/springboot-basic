package org.prgrms.kdt.engine.voucher.repository;

import org.prgrms.kdt.engine.voucher.VoucherType;
import org.prgrms.kdt.engine.voucher.domain.Voucher;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
    Optional<Map<UUID, Voucher>> getAll();
    Voucher insert(Voucher voucher);
    void setCustomerId(UUID voucherId, UUID customerId);
    Optional<Map<UUID, Voucher>> getCustomerVoucher(UUID customerId);
    void deleteCustomerVoucher(UUID customerId);
    void deleteVoucher(UUID voucherId);
    Optional<UUID> findCustomerByVoucher(UUID voucherId);
    List<Voucher> findByType(VoucherType type);
    List<Voucher> findByCreatedDate(LocalDateTime createdDate);
}

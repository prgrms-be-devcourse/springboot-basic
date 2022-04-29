package org.prgrms.kdtspringvoucher.voucher.repository;

import org.prgrms.kdtspringvoucher.voucher.entity.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
    List<Voucher> findAll();
    List<Voucher> findVouchersByCustomer(UUID customerId);
    Optional<Voucher> save(Voucher voucher);
    void allocateVoucherToCustomer(UUID customerId, UUID voucherId);
    void deleteVoucher(UUID voucherId);
    void deleteVoucherFromCustomer(UUID customerId, UUID voucherId);
    boolean validateVoucher(UUID voucherId);
}

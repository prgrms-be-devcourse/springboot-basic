package org.prgrms.kdtspringvoucher.repository.voucher;

import org.prgrms.kdtspringvoucher.entity.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
    List<Voucher> findAll();
    List<Voucher> findVouchersByCustomer(UUID customerId);
    List<Voucher> findByCreatedAt();
    List<Voucher> findByVoucherType(int voucherTypeNum);
    Optional<Voucher> save(Voucher voucher);
    Optional<Voucher> update(Voucher voucher);
    void allocateVoucherToCustomer(UUID customerId, UUID voucherId);
    void deleteVoucher(UUID voucherId);
    void deleteVoucherFromCustomer(UUID customerId, UUID voucherId);
    boolean isExistVoucher(UUID voucherId);
}

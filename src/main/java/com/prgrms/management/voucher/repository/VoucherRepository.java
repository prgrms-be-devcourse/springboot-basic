package com.prgrms.management.voucher.repository;

import com.prgrms.management.voucher.domain.Voucher;
import com.prgrms.management.voucher.domain.VoucherType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Voucher save(Voucher voucher);
    Optional<Voucher> findById(UUID voucherId);
    void updateVoucherWithCustomerId(UUID voucherId,UUID customerId);
    List<Voucher> findAll();

    void deleteByCustomerId(UUID customerId);

    List<UUID> findCustomerIdByVoucherType(VoucherType voucherType);
}

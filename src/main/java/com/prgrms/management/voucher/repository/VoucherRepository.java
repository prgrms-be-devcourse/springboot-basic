package com.prgrms.management.voucher.repository;

import com.prgrms.management.voucher.domain.Voucher;
import com.prgrms.management.voucher.domain.VoucherType;

import java.util.List;
import java.util.UUID;

public interface VoucherRepository {
    Voucher save(Voucher voucher);
    Voucher findById(UUID voucherId);

    List<Voucher> findAll();

    void deleteByCustomerId(UUID customerId);

    void findCustomerIdByVoucherType(VoucherType voucherType);
}

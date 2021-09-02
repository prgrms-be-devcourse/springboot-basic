package com.prgrms.devkdtorder.voucher.repository;

import com.prgrms.devkdtorder.customer.domain.Customer;
import com.prgrms.devkdtorder.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Optional<Voucher> findById(UUID voucherId);

    Voucher insert(Voucher voucher);

    Voucher update(Voucher voucher);

    List<Voucher> findAll();

    void deleteAll();
}

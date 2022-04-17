package com.prgms.management.voucher.repository;

import com.prgms.management.voucher.entity.Voucher;

import java.util.List;
import java.util.UUID;

public interface VoucherRepository {
    Voucher findById(UUID voucherId);

    List<Voucher> findAll();

    Voucher save(Voucher voucher);
}

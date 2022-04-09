package com.mountain.voucherApp.repository;

import com.mountain.voucherApp.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
    List<Voucher> findAll();
    Voucher insert(Voucher voucher);
}

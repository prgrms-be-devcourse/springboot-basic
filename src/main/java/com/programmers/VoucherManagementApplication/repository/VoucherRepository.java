package com.programmers.VoucherManagementApplication.repository;

import com.programmers.VoucherManagementApplication.voucher.Voucher;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Voucher addVoucher(Voucher voucher);

    Map<UUID, Voucher> findAll();

    Optional<Voucher> findById(UUID voucherId);
}
package com.program.voucher.repository;

import com.program.voucher.model.Voucher;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);

    Voucher insertVoucher(Voucher voucher);

    List<Voucher> findAll();
}

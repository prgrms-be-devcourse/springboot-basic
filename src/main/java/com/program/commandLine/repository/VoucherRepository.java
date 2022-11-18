package com.program.commandLine.repository;

import com.program.commandLine.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);

    Voucher insertVoucher(Voucher voucher);

    List<Voucher> findAll();
}

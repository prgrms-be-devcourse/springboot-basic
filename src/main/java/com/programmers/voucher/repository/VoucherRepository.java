package com.programmers.voucher.repository;

import com.programmers.voucher.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID id);

    List<Voucher> findAllVouchers();

    Voucher registerVoucher(Voucher voucher);

    void deleteAll();

    void deleteVoucher(UUID voucherId);

    List<Voucher> findByType(String name);
}

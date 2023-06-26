package com.dev.voucherproject.model.storage.voucher;

import com.dev.voucherproject.model.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherStorage {
    Optional<Voucher> findById(UUID voucherId);

    Voucher insert(Voucher voucher);

    List<Voucher> findAll();
}

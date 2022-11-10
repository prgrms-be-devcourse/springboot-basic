package com.programmers.kwonjoosung.springbootbasicjoosung.repository.voucher;

import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Voucher insert(UUID voucherID, Voucher voucher);

    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findAll();
}

package com.programmers.application.repository.voucher;

import com.programmers.application.domain.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Voucher save(Voucher voucher);

    List<Voucher> findAll();

    Optional<Voucher> findByVoucherId(UUID voucherId);
}

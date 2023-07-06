package com.programmers.voucher.repository.voucher;

import com.programmers.voucher.entity.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Voucher insert(Voucher voucher);

    List<Voucher> findAll();

    Optional<Voucher> findById(UUID voucherId);

    Voucher update(Voucher voucher);

    void delete(UUID voucherId);
}

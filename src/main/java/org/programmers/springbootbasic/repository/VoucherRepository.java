package org.programmers.springbootbasic.repository;

import org.programmers.springbootbasic.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Voucher insert(Voucher voucher);

    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findAll();

    void remove(UUID voucherId);
}

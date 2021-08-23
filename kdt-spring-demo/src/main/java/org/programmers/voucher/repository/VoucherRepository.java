package org.programmers.voucher.repository;

import org.programmers.voucher.model.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findAllVouchers();

    void save(Voucher voucher);

}

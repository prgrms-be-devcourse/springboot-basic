package org.programmers.kdtspringdemo.voucher.repository;

import org.programmers.kdtspringdemo.voucher.model.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
    Voucher insert(Voucher voucher);

    List<String> getVoucherList();
}

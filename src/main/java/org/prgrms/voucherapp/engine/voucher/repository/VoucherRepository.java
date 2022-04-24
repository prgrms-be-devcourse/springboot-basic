package org.prgrms.voucherapp.engine.voucher.repository;

import org.prgrms.voucherapp.engine.voucher.entity.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);

    Voucher insert(Voucher voucher);

    List<Voucher> findAll();

    Voucher update(Voucher voucher);

    void deleteById(UUID voucherId);
}

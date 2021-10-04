package org.prgrms.dev.voucher.repository;

import org.prgrms.dev.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findAll();

    Voucher insert(Voucher voucher);

    Voucher update(Voucher voucher);

    void deleteById(UUID voucherId);
}

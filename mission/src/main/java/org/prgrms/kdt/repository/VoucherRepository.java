package org.prgrms.kdt.repository;

import org.prgrms.kdt.model.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);

    Voucher insert(Voucher voucher);

    Optional<List<Voucher>> findAll();
}

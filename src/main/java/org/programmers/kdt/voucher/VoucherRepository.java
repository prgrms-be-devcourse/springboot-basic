package org.programmers.kdt.voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
    Voucher save(Voucher voucher);
    List<Voucher> findAll();
}

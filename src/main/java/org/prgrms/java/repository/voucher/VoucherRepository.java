package org.prgrms.java.repository.voucher;

import org.prgrms.java.domain.voucher.Voucher;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);

    Collection<Voucher> findAll();

    Voucher insert(Voucher voucher);

    Voucher update(Voucher voucher);

    long deleteAll();
}

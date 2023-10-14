package org.prgrms.vouchermanager.Repository;

import org.prgrms.vouchermanager.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Voucher save(Voucher voucher);

    Optional<Voucher> findByID(UUID voucherId);

    List<Voucher> findAll();
}

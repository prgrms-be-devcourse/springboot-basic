package org.prgrms.kdt.shop.repository;

import org.prgrms.kdt.shop.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    public List<Voucher> getStorage( );

    Optional<Voucher> findById(UUID voucherId);

    Voucher insert(Voucher voucher);
}

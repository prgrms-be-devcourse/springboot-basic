package org.prgrms.kdt.shop.repository;

import org.prgrms.kdt.shop.domain.Voucher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VoucherRepository {
    public List<Voucher> findByAll( );

    Optional<Voucher> findById(UUID voucherId);

    Voucher insert(Voucher voucher);
}

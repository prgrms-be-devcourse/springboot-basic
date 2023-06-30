package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.model.Voucher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VoucherRepository {
    Optional<Voucher> findByiD(UUID voucherId);
    Voucher insert(Voucher voucher);
    List<Voucher> findAll();
}

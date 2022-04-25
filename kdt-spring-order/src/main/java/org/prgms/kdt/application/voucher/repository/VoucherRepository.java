package org.prgms.kdt.application.voucher.repository;

import java.util.Optional;
import org.prgms.kdt.application.voucher.domain.Voucher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VoucherRepository {
    Voucher insert(Voucher voucher);
    List<Voucher> findAll();
    Optional<Voucher> findByVoucherId(UUID voucherId);
    List<Voucher> findByCustomerId(UUID customerId);
    Voucher updateDiscountValue(Voucher voucher);
    int deleteById(UUID voucherId);
    int deleteAll();
}

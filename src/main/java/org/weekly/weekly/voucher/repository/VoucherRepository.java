package org.weekly.weekly.voucher.repository;

import org.weekly.weekly.voucher.domain.DiscountType;
import org.weekly.weekly.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Voucher insert(Voucher voucher);

    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findAll();

    List<Voucher> findByDiscountType(DiscountType discountType);

    Voucher update(Voucher voucher);

    void deleteById(UUID voucherId);

    void deleteAll();
}

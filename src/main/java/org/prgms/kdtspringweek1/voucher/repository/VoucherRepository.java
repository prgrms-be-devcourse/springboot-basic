package org.prgms.kdtspringweek1.voucher.repository;

import org.prgms.kdtspringweek1.voucher.entity.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Voucher save(Voucher voucher);

    List<Voucher> findAllVouchers();

    Optional<Voucher> findById(UUID voucherId);

    Voucher update(Voucher voucher);

    void deleteAll();

    void deleteById(UUID voucherId);
}

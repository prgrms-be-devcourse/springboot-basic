package org.programmers.weekly.mission.domain.voucher.repository;

import org.programmers.weekly.mission.domain.voucher.model.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Voucher insert(Voucher voucher);
    List<Voucher> findAllVouchers();
    Optional<Voucher> findById(UUID voucherId);
    Voucher update(Voucher voucher);
    void deleteAll();
    void deleteById(UUID voucherId);
}

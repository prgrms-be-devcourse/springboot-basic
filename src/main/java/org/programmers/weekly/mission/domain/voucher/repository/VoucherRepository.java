package org.programmers.weekly.mission.domain.voucher.repository;

import org.programmers.weekly.mission.domain.voucher.model.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Voucher insert(Voucher voucher);
    Optional<Voucher> findById(UUID voucherId);
    List<Voucher> getVoucherList();
}

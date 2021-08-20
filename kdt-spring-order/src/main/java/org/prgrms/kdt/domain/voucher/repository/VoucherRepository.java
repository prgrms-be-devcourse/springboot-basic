package org.prgrms.kdt.domain.voucher.repository;

import org.prgrms.kdt.domain.voucher.domain.Voucher;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);

    Map<UUID, Voucher> findAll();

    boolean save(Voucher voucher);
}
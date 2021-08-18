package org.prgrms.kdt.repository;

import org.prgrms.kdt.Voucher;

import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
}

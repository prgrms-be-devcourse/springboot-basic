package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findId(UUID voucherId);
    void insert(Optional<Voucher> voucher);
}

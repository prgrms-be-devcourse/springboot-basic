package org.prgrms.kdt.repository;

import org.prgrms.kdt.voucher.Voucher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
    void insert (Voucher voucher);
    List<Voucher> getAllStoredVoucher();
    void clear();
}

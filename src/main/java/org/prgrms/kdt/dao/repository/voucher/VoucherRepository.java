package org.prgrms.kdt.dao.repository.voucher;

import org.prgrms.kdt.dao.entity.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);

    void insert(Voucher voucher);

    List<Voucher> getAllStoredVoucher();

    void clear();
}

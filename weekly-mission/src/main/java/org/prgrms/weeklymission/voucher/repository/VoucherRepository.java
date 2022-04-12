package org.prgrms.weeklymission.voucher.repository;

import org.prgrms.weeklymission.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    void save(Voucher voucher);
    Optional<Voucher> findById(UUID voucherId);
    List<Voucher> findAll();
    int countStorageSize();
    void clear();
}
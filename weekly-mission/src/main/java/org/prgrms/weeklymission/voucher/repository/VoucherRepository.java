package org.prgrms.weeklymission.voucher.repository;

import org.prgrms.weeklymission.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
    void save(Voucher voucher);
    List<Voucher> findAll();
    int getStorageSize();
    void clear();
}
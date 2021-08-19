package org.prgrms.orderApp.repository;

import org.prgrms.orderApp.model.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
    int save(Voucher voucher);
    List<Voucher> findAll();
}

package org.prgms.order.voucher.repository;

import org.prgms.order.voucher.model.Voucher;

import java.util.Optional;
import java.util.UUID;


public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
    Voucher insert(Voucher voucher);
}

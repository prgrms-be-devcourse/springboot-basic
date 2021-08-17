package org.programmers.kdt.voucher;

import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
    void addVoucher(Voucher voucher);
    void listAllVouchers();
}

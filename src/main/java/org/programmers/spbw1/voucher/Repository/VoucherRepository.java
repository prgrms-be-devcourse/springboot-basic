package org.programmers.spbw1.voucher.Repository;

import org.programmers.spbw1.voucher.Voucher;

import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> getVoucherById(UUID id);
    Voucher insert(Voucher voucher);
    int getStoredVoucherNum();
    void clear();
    void showAllVouchers();
}

package org.programmers.spbw1.voucher;

import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> getVoucherById(UUID id);
    Voucher insert(Voucher voucher);
    void clear();
}

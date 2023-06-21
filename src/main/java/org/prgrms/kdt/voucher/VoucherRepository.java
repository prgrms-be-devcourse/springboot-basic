package org.prgrms.kdt.voucher;

import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findByiD(UUID voucherId);
    Voucher insert(Voucher voucher);
}

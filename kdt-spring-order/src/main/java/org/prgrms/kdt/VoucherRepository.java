package org.prgrms.kdt;

import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    public Optional<Voucher> findById(UUID voucherId);
}

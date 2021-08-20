package org.prgrms.kdt.engine.voucher;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
    Optional<Map<UUID, Voucher>> getAll();
    Voucher insert(Voucher voucher);
}

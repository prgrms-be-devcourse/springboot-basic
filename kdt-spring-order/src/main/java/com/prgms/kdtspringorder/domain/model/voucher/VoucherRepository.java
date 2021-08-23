package com.prgms.kdtspringorder.domain.model.voucher;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);

    Voucher save(Voucher voucher);

    Map<UUID, Voucher> findAll();
}

package com.programmers.voucher;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Optional<Voucher> findByID(UUID voucherID);

    Voucher insert(Voucher voucher);

    Map<UUID, Voucher> findAll();
}

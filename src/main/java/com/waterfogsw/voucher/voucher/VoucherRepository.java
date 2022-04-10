package com.waterfogsw.voucher.voucher;

import java.util.List;
import java.util.UUID;

public interface VoucherRepository {
    void save(Voucher voucher);

    List<Voucher> findAll();

    Voucher findById(UUID voucherId);
}

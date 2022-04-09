package com.waterfogsw.voucher.voucher;

import java.util.UUID;

public class Voucher {
    private final UUID voucherId;
    private final Double value;
    private final VoucherType type;

    public Voucher(UUID voucherId, Double value, VoucherType type) {
        this.voucherId = voucherId;
        this.value = value;
        this.type = type;
    }
}

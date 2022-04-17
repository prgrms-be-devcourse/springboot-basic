package com.waterfogsw.voucher.voucher.domain;

import java.util.UUID;

public class Voucher {
    UUID voucherId;
    VoucherType type;
    Double value;

    public Voucher(UUID voucherId, VoucherType type, Double value) {
        this.voucherId = voucherId;
        this.type = type;
        this.value = value;
    }
}

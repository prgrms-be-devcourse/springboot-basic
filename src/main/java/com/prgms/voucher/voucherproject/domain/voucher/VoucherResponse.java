package com.prgms.voucher.voucherproject.domain.voucher;

import builder.builderEntity.Entity;

import java.util.UUID;

public class VoucherResponse implements Entity {
    private final UUID voucher_id;
    private final String voucher_type;
    private final long discount;

    private VoucherResponse(UUID voucherId, String voucherType, long discount) {
        voucher_id = voucherId;
        voucher_type = voucherType;
        this.discount = discount;
    }
}

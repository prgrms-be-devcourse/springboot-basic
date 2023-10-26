package org.prgrms.kdt.voucher.controller;

import java.util.UUID;

public class FixedAmountVoucherDto {
    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucherDto(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getAmount() {
        return amount;
    }
}

package org.prgrms.kdt.voucher.controller;

import java.util.UUID;

public class PercentDiscountVoucherDto {
    private final UUID voucherId;
    private final int percent;

    public PercentDiscountVoucherDto(UUID voucherId, int percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public int getPercent() {
        return percent;
    }
}

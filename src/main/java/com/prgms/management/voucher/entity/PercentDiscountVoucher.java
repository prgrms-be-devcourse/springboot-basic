package com.prgms.management.voucher.entity;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private UUID voucherId;
    private final Long percent;

    public PercentDiscountVoucher(Long percent) {
        this.voucherId = UUID.randomUUID();
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public void resetVoucherId() {
        voucherId = UUID.randomUUID();
    }

    @Override
    public Long discount(Long beforeDiscount) {
        return beforeDiscount * ((100 - percent) / 100);
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher {" +
                "voucherId=" + voucherId +
                ", percent=" + percent +
                '}';
    }
}

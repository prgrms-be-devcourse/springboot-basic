package com.prgms.management.voucher.entity;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private UUID voucherId;
    private final Integer percent;

    public PercentDiscountVoucher(Integer percent) {
        this.voucherId = UUID.randomUUID();
        this.percent = percent;
    }

    public PercentDiscountVoucher(UUID voucherId, Integer percent) {
        this.voucherId = voucherId;
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
    public String getStringForCSV() {
        return this.getClass().getCanonicalName() + "," + voucherId + "," + percent;
    }

    @Override
    public String toString() {
        return "Percent Discount Voucher {" +
                "voucherId=" + voucherId +
                ", percent=" + percent +
                '}';
    }
}

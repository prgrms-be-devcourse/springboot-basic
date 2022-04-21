package org.prgrms.vouchermanager.voucher.domain;

import java.util.UUID;

public class PercentDiscountVoucher extends AbstractVoucher {

    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long amount) {
        super(voucherId, VoucherType.PERCENT);
        if (amount <= 0 || amount > 100) throw new IllegalArgumentException("Percent should be between 0 and 100");
        this.percent = amount;
    }

    @Override
    public long getDiscountValue() {
        return percent;
    }

    @Override
    public Long discount(long beforeDiscount) {
        return Math.round(beforeDiscount * (100 - percent) / 100.0);
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "voucherId=" + getVoucherId() +
                ", voucherType=" + getVoucherType() +
                ", percent=" + percent +
                '}';
    }
}

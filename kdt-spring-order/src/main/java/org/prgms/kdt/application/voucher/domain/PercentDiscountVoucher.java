package org.prgms.kdt.application.voucher.domain;

import lombok.Getter;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private UUID voucherId;
    private final VoucherType voucherType = VoucherType.PERCENT_DISCOUNT;
    private final long percent = 10L;

    public PercentDiscountVoucher(UUID voucherId) {
        this.voucherId = voucherId;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "voucherId=" + voucherId +
                ", voucherType=" + voucherType +
                ", percent=" + percent +
                '}';
    }
}

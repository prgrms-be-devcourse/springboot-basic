package org.prgrms.kdtspringdemo.voucher.domain;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher{
    private final UUID voucherId;
    private final long percent;
    private final String voucherType;

    public PercentDiscountVoucher(UUID voucherId, long percent, String voucherType) {
        if(percent <= 0 || percent > 100) {
            throw new RuntimeException("할인률은 1~100까지만 적용 가능합니다.");
        }
        this.voucherId = voucherId;
        this.percent = percent;
        this.voucherType = voucherType;
    }

    @Override
    public UUID getVoucherId() {
        return this.voucherId;
    }

    @Override
    public Long getAmount() {
        return this.percent;
    }

    @Override
    public String getVoucherType() {
        return this.voucherType;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (1 - percent/100);
    }
}

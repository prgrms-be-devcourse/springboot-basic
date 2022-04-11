package com.prgrms.vouchermanagement.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final long discountPrice;

    public FixedAmountVoucher(UUID voucherId, long discountPrice) {
        this.voucherId = voucherId;
        this.discountPrice = discountPrice;
    }

    @Override
    public long discount(long beforeDiscountPrice) {
        return beforeDiscountPrice - discountPrice;
    }

    @Override
    public UUID getId() {
        return voucherId;
    }

    @Override
    public long getAmount() {
        return discountPrice;
    }

    @Override
    public String toString() {
        return "Id=" + getId() + ", Type=FixedAmount, discountPrice="+ discountPrice +"won";
    }
}

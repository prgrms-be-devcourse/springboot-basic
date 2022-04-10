package com.prgrms.vouchermanagement.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final long discountPrice;

    public FixedAmountVoucher(long discountPrice) {
        this.voucherId = UUID.randomUUID();
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
    public String toString() {
        return "Id=" + getId() + ", Type=FixedAmount, discountPrice="+ discountPrice +"won";
    }
}

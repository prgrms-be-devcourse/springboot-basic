package org.prgrms.springbootbasic.entity.voucher;


import java.util.UUID;

public class PercentAmountVoucher extends Voucher {
    private final long percent;

    public PercentAmountVoucher(UUID voucherId, long percent) {
        super(voucherId);
        this.percent = percent;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (100 - percent);
    }

    @Override
    public long getQuantity() {
        return percent;
    }

    @Override
    public String toString() {
        return "{PercentAmountVoucher- id: %s, percent: %d}".formatted(voucherId, percent);
    }
}

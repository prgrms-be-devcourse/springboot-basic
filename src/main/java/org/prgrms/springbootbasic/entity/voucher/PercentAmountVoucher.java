package org.prgrms.springbootbasic.entity.voucher;


public class PercentAmountVoucher extends Voucher {
    private final long percent;

    public PercentAmountVoucher(long percent) {
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

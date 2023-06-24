package org.devcourse.voucher.domain;

public class FixedAmountVoucher extends Voucher {
    private final String type = "FIXED";
    private final long discountAmount;


    public FixedAmountVoucher(long discountAmount) {
        super();
        this.discountAmount = discountAmount;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - discountAmount;
    }

    public long getDiscountAmount() {
        return discountAmount;
    }
}

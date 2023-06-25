package org.devcourse.voucher.domain;

public class FixedAmountVoucher extends Voucher {
    private final String TYPE = "FIXED";
    private final long discountAmount;


    public FixedAmountVoucher(long discountAmount) {
        super();
        this.discountAmount = discountAmount;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public long discount(long beforeDiscount)  throws IllegalArgumentException{
        if (beforeDiscount < 0) {
            throw new IllegalArgumentException("beforeDiscount는 음수일 수 없습니다.");
        }
        return beforeDiscount - discountAmount;
    }

    public long getDiscountAmount() {
        return discountAmount;
    }
}

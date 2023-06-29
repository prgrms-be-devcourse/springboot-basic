package org.devcourse.voucher.domain;

public class FixedAmountVoucher extends Voucher {
    private final VoucherType type = VoucherType.FIXED;
    private final long discountAmount;


    public FixedAmountVoucher(long discountAmount) {
        super();
        this.discountAmount = discountAmount;
    }

    public VoucherType getType() {
        return type;
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

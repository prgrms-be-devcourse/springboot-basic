package org.prgms.voucher.voucher;

public class FixedAmountVoucher extends AmountVoucher {
    private final int discountAmount;

    public FixedAmountVoucher(int discountAmount) {
        super(AmountVoucherCreationType.FIXED_AMOUNT);
        this.discountAmount = validateDiscountAmount(discountAmount);
    }

    public int discount(int originalPrice) {
        return Math.max((originalPrice - discountAmount), 0);
    }

    private int validateDiscountAmount(int discountAmount) {
        if (discountAmount <= 0) {
            throw new IllegalArgumentException("할인 값은 0보다 커야합니다.");
        }

        return discountAmount;
    }
}

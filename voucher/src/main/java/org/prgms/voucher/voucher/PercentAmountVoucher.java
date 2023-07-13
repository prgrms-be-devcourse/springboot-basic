package org.prgms.voucher.voucher;

public class PercentAmountVoucher extends AmountVoucher {
    private final int discountAmount;

    public PercentAmountVoucher(int discountAmount) {
        super(discountAmount, AmountVoucherCreationType.PERCENT_AMOUNT);
        this.discountAmount = validateDiscountAmount(discountAmount);
    }

    @Override
    public int discount(int originalPrice) {
        return Math.max(originalPrice - (originalPrice * discountAmount / 100), 0);
    }

    @Override
    public int validateDiscountAmount(int discountAmount) {
        if (discountAmount <= 0 || discountAmount > 100) {
            throw new IllegalArgumentException("할인 값은 0초과 100이하여야 합니다.");
        }

        return discountAmount;
    }
}

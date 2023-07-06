package org.prgms.voucher.voucher;

public class FixedAmountVoucher extends AmountVoucher {
    public FixedAmountVoucher(int originalPrice, int discountAmount) {
        super(originalPrice, discountAmount, AmountVoucherOptionType.FIXED_AMOUNT);
    }

    @Override
    public int discount(int originalPrice) {
        return Math.max((originalPrice - super.discountAmount), 0);
    }
}

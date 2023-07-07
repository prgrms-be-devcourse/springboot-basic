package org.prgms.voucher.voucher;

public class PercentAmountVoucher extends AmountVoucher {
    public PercentAmountVoucher(int discountAmount) {
        super(discountAmount, AmountVoucherOptionType.PERCENT_AMOUNT);
    }

    @Override
    public int discount(int originalPrice) {
        return Math.max(originalPrice - (originalPrice * super.discountAmount / 100), 0);
    }
}

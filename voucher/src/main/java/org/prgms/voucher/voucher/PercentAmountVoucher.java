package org.prgms.voucher.voucher;

public class PercentAmountVoucher extends AmountVoucher {
    public PercentAmountVoucher(int discountAmount) {
        super(discountAmount);
        super.amountVoucherOptionType = AmountVoucherOptionType.PERCENT_AMOUNT;
    }

    @Override
    public int discount(int amountBeforeDiscount) {
        return Math.max(amountBeforeDiscount - (amountBeforeDiscount * super.discountAmount / 100), 0);
    }
}

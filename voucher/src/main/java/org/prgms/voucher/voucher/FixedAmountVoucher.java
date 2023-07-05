package org.prgms.voucher.voucher;

public class FixedAmountVoucher extends AmountVoucher {
    public FixedAmountVoucher(int discountAmount) {
        super(discountAmount);
        super.amountVoucherOptionType = AmountVoucherOptionType.FIXED_AMOUNT;
    }

    @Override
    public int discount(int amountBeforeDiscount) {
        return Math.max((amountBeforeDiscount - super.discountAmount), 0);
    }
}

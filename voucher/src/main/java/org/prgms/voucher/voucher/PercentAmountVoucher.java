package org.prgms.voucher.voucher;

public class PercentAmountVoucher extends AmountVoucher {
    public PercentAmountVoucher(int initialMoney, int discountAmount) {
        super(initialMoney, discountAmount, AmountVoucherOptionType.PERCENT_AMOUNT);
    }

    @Override
    public int discount(int amountBeforeDiscount) {
        return Math.max(amountBeforeDiscount - (amountBeforeDiscount * super.discountAmount / 100), 0);
    }
}

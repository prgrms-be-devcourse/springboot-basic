package org.prgms.voucher.voucher;

import java.util.UUID;

public class FixedAmountVoucher extends AmountVoucher {
    public FixedAmountVoucher(UUID id, int discountAmount) {
        super(id, discountAmount);
        super.amountVoucherOptionType = AmountVoucherOptionType.FIXED_AMOUNT;
    }

    @Override
    public int discount(int amountBeforeDiscount) {
        return Math.max((amountBeforeDiscount - super.discountAmount), 0);
    }
}

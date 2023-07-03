package org.prgms.voucher.voucher;

import java.util.UUID;

public class PercentAmountVoucher extends AmountVoucher {
    public PercentAmountVoucher(UUID id, int discountAmount) {
        super(id, discountAmount);
        super.amountVoucherOptionType = AmountVoucherOptionType.PERCENT_AMOUNT;
    }

    @Override
    public int discount(int amountBeforeDiscount) {
        return Math.max(amountBeforeDiscount - (amountBeforeDiscount * super.discountAmount / 100), 0);
    }
}

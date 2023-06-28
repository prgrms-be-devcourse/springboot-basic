package org.prgms.voucher.voucher;

import org.prgms.voucher.type.AmountType;

public class VoucherCreateDto {
    private int discountAmount;
    private AmountType amountType;

    public int getDiscountAmount() {
        return discountAmount;
    }

    public AmountType getAmountType() {
        return amountType;
    }
}

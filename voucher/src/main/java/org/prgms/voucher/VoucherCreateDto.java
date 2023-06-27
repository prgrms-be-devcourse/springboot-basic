package org.prgms.voucher;

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

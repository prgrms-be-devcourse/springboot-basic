package org.prgms.voucher.voucher;

public class AmountVoucherVo {
    private final AmountVoucherCreationType amountVoucherCreationType;
    private final int discountAmount;

    public AmountVoucherVo(AmountVoucherCreationType amountVoucherCreationType, int discountAmount) {
        this.amountVoucherCreationType = amountVoucherCreationType;
        this.discountAmount = discountAmount;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public AmountVoucherCreationType getAmountVoucherCreationType() {
        return amountVoucherCreationType;
    }
}

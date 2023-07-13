package org.prgms.voucher.voucher;

public class AmountVoucherCreateDto {
    private final AmountVoucherCreationType amountVoucherCreationType;
    private final int discountAmount;

    public AmountVoucherCreateDto(AmountVoucherCreationType amountVoucherCreationType, int discountAmount) {
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

package org.prgms.voucher.voucher;

public class AmountVoucherCreateVo {
    private final AmountVoucherCreationType amountVoucherCreationType;
    private final int originalPrice;
    private final int discountAmount;

    public AmountVoucherCreateVo(AmountVoucherCreationType amountVoucherCreationType, int originalPrice, int discountAmount) {
        this.amountVoucherCreationType = amountVoucherCreationType;
        this.originalPrice = originalPrice;
        this.discountAmount = discountAmount;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public AmountVoucherCreationType getAmountVoucherCreationType() {
        return amountVoucherCreationType;
    }
}

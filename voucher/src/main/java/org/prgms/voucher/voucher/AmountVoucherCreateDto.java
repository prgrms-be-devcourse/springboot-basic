package org.prgms.voucher.voucher;

public class AmountVoucherCreateDto {
    private final AmountVoucherCreationType amountVoucherCreationType;
    private final int originalPrice;
    private final int discountAmount;

    public AmountVoucherCreateDto(AmountVoucherCreationType amountVoucherCreationType, int originalPrice, int discountAmount) {
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

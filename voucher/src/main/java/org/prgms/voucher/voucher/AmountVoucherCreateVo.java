package org.prgms.voucher.voucher;

public class AmountVoucherCreateVo {
    private final AmountVoucherOptionType amountVoucherOptionType;
    private final int originalPrice;
    private final int discountAmount;

    public AmountVoucherCreateVo(AmountVoucherOptionType amountVoucherOptionType, int originalPrice, int discountAmount) {
        this.amountVoucherOptionType = amountVoucherOptionType;
        this.originalPrice = originalPrice;
        this.discountAmount = discountAmount;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public AmountVoucherOptionType getAmountVoucherOptionType() {
        return amountVoucherOptionType;
    }
}

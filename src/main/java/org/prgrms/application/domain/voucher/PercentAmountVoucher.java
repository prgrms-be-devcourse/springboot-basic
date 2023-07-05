package org.prgrms.application.domain.voucher;

public class PercentAmountVoucher extends Voucher {

    private static final double MAX_DISCOUNT_VALUE = 100;
    private VoucherType voucherType;

    public PercentAmountVoucher(Long voucherId, VoucherType voucherType, double discountAmount) {
        validatePercent(discountAmount);
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discountAmount = discountAmount;
    }

    private void validatePercent(double percentAmount) {
        if (percentAmount <= MIN_DISCOUNT_VALUE || percentAmount >= MAX_DISCOUNT_VALUE) {
            throw new IllegalArgumentException("잘못된 입력 범위입니다.");
        }
    }

    public double getPercentAmount() {
        return discountAmount;
    }

    public void changePercentAmount(double percentAmount) {
        validatePercent(percentAmount);
    }

    @Override
    public Long getVoucherId() {
        return voucherId;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public double getDiscountAmount() {
        return discountAmount;
    }

    @Override
    public double discount(double beforeDiscount) {
        return beforeDiscount * (discountAmount / 100);
    }
}

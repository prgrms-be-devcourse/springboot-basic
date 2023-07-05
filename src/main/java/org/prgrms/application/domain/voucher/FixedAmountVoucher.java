package org.prgrms.application.domain.voucher;

public class FixedAmountVoucher extends Voucher {

    private static final int MIN_DISCOUNT_VALUE = 0;
    private VoucherType voucherType;

    public FixedAmountVoucher(Long voucherId, VoucherType voucherType, double discountAmount) {
        validatePositive(discountAmount);
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discountAmount = discountAmount;
    }

    public double getFixedAmount() {
        return discountAmount;
    }

    public void changeFixedAmount(double discountAmount) { //수정
        validatePositive(discountAmount);
        this.discountAmount = discountAmount;
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
        return 0;
    }

    @Override
    public double discount(double beforeDiscount) {
        validatePositive(beforeDiscount - discountAmount);
        double discountedAmount = beforeDiscount - discountAmount;
        return discountedAmount;
    }

    private void validatePositive(double amount) {
        if (amount <= MIN_DISCOUNT_VALUE) throw new IllegalArgumentException("금액은 양수여야 합니다.");
    }

}

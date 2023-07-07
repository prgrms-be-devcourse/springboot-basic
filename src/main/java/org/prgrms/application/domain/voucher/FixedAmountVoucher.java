package org.prgrms.application.domain.voucher;

public class FixedAmountVoucher extends Voucher {

    private VoucherType voucherType;

    public FixedAmountVoucher(Long voucherId, VoucherType voucherType, double discountAmount) {
        super(voucherId,discountAmount);
        validatePositive(discountAmount);
        this.voucherType = voucherType;
    }

    public double getFixedAmount() {
        return discountAmount;
    }

    public void changeFixedAmount(double discountAmount) {
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
        return discountAmount;
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

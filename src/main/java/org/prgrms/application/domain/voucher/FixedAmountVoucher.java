package org.prgrms.application.domain.voucher;

public class FixedAmountVoucher extends Voucher {

    public FixedAmountVoucher(Long voucherId, double discountAmount) {
        super(voucherId,discountAmount);
        validatePositive(discountAmount);
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
        return VoucherType.FIXED;
    }

    @Override
    public double getDiscountAmount() {
        return discountAmount;
    }

    @Override
    public double discount(double beforeDiscount) {
        validatePositive(beforeDiscount - discountAmount);
        return beforeDiscount - discountAmount;
    }

    private void validatePositive(double amount) {
        if (amount <= MIN_DISCOUNT_VALUE) throw new IllegalArgumentException("금액은 양수여야 합니다.");
    }

}

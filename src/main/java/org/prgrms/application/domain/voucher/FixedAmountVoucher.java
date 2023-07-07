package org.prgrms.application.domain.voucher;

public class FixedAmountVoucher extends Voucher {

    public FixedAmountVoucher(Long voucherId, double discountAmount) {
        super(voucherId,discountAmount);
        validatePositive(discountAmount);
    }

    private void validatePositive(double amount) {
        if (amount <= MIN_DISCOUNT_VALUE) throw new IllegalArgumentException("금액은 양수여야 합니다.");
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.FIXED;
    }

    @Override
    public double discount(double beforeDiscount) {
        validatePositive(beforeDiscount - discountAmount);
        return beforeDiscount - discountAmount;
    }

}

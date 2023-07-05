package org.prgrms.application.domain.voucher;

public class FixedAmountVoucher extends Voucher {

    public FixedAmountVoucher(Long voucherId, VoucherType voucherType, double discountAmount) {
        validatePositive(discountAmount);
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discountAmount = discountAmount;
    }

    private static void validatePositive(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("금액은 양수여야 합니다.");
    }

    public double getFixedAmount() {
        return discountAmount;
    }

    public void changeFixedAmount(double fixedAmount) {
        validatePositive(fixedAmount);
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
    public double discount(double beforeDiscount) {
        double discountedAmount = beforeDiscount - discountAmount;
        validatePositive(discountedAmount);
        return discountedAmount;
    }

}

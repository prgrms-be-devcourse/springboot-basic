package org.prgrms.application.domain.voucher;

public class PercentAmountVoucher extends Voucher {

    private static final double MAX_DISCOUNT_VALUE = 100;
    private VoucherType voucherType;

    public PercentAmountVoucher(Long voucherId, double discountAmount) {
        super(voucherId,discountAmount);
        validatePercent(discountAmount);
    }

    private void validatePercent(double percentAmount) {
        if (percentAmount <= MIN_DISCOUNT_VALUE || percentAmount >= MAX_DISCOUNT_VALUE) {
            throw new IllegalArgumentException("잘못된 입력 범위입니다.");
        }
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.PERCENT;
    }

    @Override
    public double discount(double beforeDiscount) {
        return beforeDiscount * (discountAmount / 100);
    }
}

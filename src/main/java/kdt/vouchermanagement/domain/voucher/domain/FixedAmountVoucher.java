package kdt.vouchermanagement.domain.voucher.domain;

public class FixedAmountVoucher extends Voucher {
    private final int MIN_VALUE = 0;

    public FixedAmountVoucher(VoucherType voucherType, int discountValue) {
        super(voucherType, discountValue);
    }

    public FixedAmountVoucher(Long voucherId, VoucherType voucherType, int discountValue) {
        super(voucherId, voucherType, discountValue);
    }

    @Override
    public void validateValueRange() {
        if (this.getDiscountValue() <= MIN_VALUE) {
            throw new IllegalArgumentException("입력한 DiscountValue 값이 유효하지 않습니다.");
        }
    }
}

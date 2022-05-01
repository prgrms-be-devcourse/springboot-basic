package kdt.vouchermanagement.domain.voucher.domain;

import java.time.LocalDateTime;

public class FixedAmountVoucher extends Voucher {
    private final int MIN_VALUE = 0;

    public FixedAmountVoucher(VoucherType voucherType, int discountValue) {
        super(voucherType, discountValue);
    }

    public FixedAmountVoucher(Long voucherId, VoucherType voucherType, int discountValue, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(voucherId, voucherType, discountValue, createdAt, updatedAt);
    }

    @Override
    public void validateValueRange() {
        if (this.getDiscountValue() <= MIN_VALUE) {
            throw new IllegalArgumentException("입력한 DiscountValue 값이 유효하지 않습니다.");
        }
    }
}

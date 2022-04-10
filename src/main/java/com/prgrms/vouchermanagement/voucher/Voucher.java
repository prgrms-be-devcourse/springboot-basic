package com.prgrms.vouchermanagement.voucher;

import com.prgrms.vouchermanagement.VoucherType;

import java.util.UUID;

public interface Voucher {
    long discount(long beforeDiscountPrice);

    UUID getId();

    static Voucher createVoucher(VoucherType type, long amount) throws IllegalArgumentException {

        if (type == VoucherType.FIXED_DISCOUNT) {
            return new FixedAmountVoucher(amount);
        }

        if (type == VoucherType.PERCENT_DISCOUNT) {
            checkAmountRightRange(amount);
            return new PercentDiscountVoucher(amount);
        }

        throw new IllegalArgumentException();
    }

    private static void checkAmountRightRange(long amount) throws IllegalArgumentException {
        if (amount < 0 || amount > 100)
            throw new IllegalArgumentException("Percent discount 는 0 ~ 100% 범위의 값을 입력해주세요.");
    }
}

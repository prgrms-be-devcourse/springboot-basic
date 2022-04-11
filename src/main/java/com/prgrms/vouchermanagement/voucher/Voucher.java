package com.prgrms.vouchermanagement.voucher;

import java.util.UUID;

import static com.prgrms.vouchermanagement.voucher.VoucherType.*;

public interface Voucher {
    long discount(long beforeDiscountPrice);

    UUID getId();

    long getAmount();

    /**
     * VoucherType 에 해당하는 Voucher 인스턴스를 생성하여 반환한다.
     */
    static Voucher createVoucher(VoucherType type, long amount) throws IllegalArgumentException {
        return createVoucher(UUID.randomUUID(), type, amount);
    }

    static Voucher createVoucher(UUID voucherId, VoucherType type, long amount) throws IllegalArgumentException {
        checkAmountRightRange(type, amount);

        if (type == FIXED_DISCOUNT) {
            return new FixedAmountVoucher(voucherId, amount);
        }

        if (type == PERCENT_DISCOUNT) {
            return new PercentDiscountVoucher(voucherId, amount);
        }

        throw new IllegalArgumentException();
    }

    /**
     * amount 값이 올바른 범위인지 확인한다.
     * - FixedDiscountVoucher : 0보다 작은 값이 입력되면 예외
     * - PercentDiscountVoucher : 0~100 범위를 벗어나는 값이 입력되면 예외
     */
    private static void checkAmountRightRange(VoucherType type, long amount) throws IllegalArgumentException {
        if (amount < 0) throw new IllegalArgumentException("0보다 작은 값은 입력할 수 없습니다.");

        if (type == PERCENT_DISCOUNT && amount > 100) {
            throw new IllegalArgumentException("Percent discount 는 0 ~ 100% 범위의 값을 입력해주세요.");
        }
    }
}

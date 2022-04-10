package com.prgrms.vouchermanagement.voucher;

import com.prgrms.vouchermanagement.VoucherType;

import java.util.UUID;

public interface Voucher {
    long discount(long beforeDiscountPrice);

    UUID getId();

    /**
     * VoucherType 에 해당하는 Voucher 인스턴스를 생성하여 반환한다.
     * @throws IllegalArgumentException : Voucher 타입이 PercentDiscount 인 경우 0~100% 의 범위를 벗어나는 수가 입력되면 예외를 던진다.
     */
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

    /**
     * Voucher 타입이 PercentDiscount 인 경우 0~100 범위의 수가 입력되었는지 확인한다.
     */
    private static void checkAmountRightRange(long amount) throws IllegalArgumentException {
        if (amount < 0 || amount > 100)
            throw new IllegalArgumentException("Percent discount 는 0 ~ 100% 범위의 값을 입력해주세요.");
    }
}

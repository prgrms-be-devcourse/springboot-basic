package com.prgrms.vouchermanagement.voucher;

import java.util.Arrays;

public enum VoucherType {
    FIXED_DISCOUNT(1), PERCENT_DISCOUNT(2);

    private final int order;

    VoucherType(int order) {
        this.order = order;
    }

    /**
     * 메뉴에서 입력받은 voucher 번호와 일치하는 VoucherType 을 반환한다.
     */
    public static VoucherType getVoucherType(int order) throws IllegalArgumentException {
        return Arrays.stream(VoucherType.values())
                .filter(v -> v.order == order)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("order와 매칭되는 VoucherType이 없습니다."));
    }

    /**
     * @throws IllegalArgumentException : amount 값이 유효한 범위의 값이 아닌 경우 of 메서드에서 던져진다.
     */
    public Voucher constructor(long amount) throws IllegalArgumentException {
        switch (this) {
            case FIXED_DISCOUNT:
                return FixedAmountVoucher.of(amount);
            case PERCENT_DISCOUNT:
                return PercentDiscountVoucher.of(amount);
            default:
                throw new IllegalArgumentException("일치하는 VoucherType이 없습니다");
        }
    }
}

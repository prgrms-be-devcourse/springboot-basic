package com.prgrms.vouchermanagement;

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
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("order와 매칭되는 VoucherType이 없습니다."));
    }
}

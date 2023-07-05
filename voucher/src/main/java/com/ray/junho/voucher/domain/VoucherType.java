package com.ray.junho.voucher.domain;

import java.util.Arrays;

public enum VoucherType {
    FIXED_AMOUNT(1),
    PERCENT_DISCOUNT(2),
    ;

    private final int voucherTypeOrder;

    VoucherType(int voucherTypeOrder) {
        this.voucherTypeOrder = voucherTypeOrder;
    }

    public static VoucherType findVoucherType(int voucherTypeOrder) {
        return Arrays.stream(values())
                .filter(voucherType -> voucherType.voucherTypeOrder == voucherTypeOrder)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 바우처 타입이 없습니다."));
    }
}

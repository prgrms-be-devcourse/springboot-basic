package com.prgrms.springbootbasic.enums;

import java.util.Arrays;

public enum VoucherType {
    FIXED("FixedDiscountVoucher"),
    PERCENT("PercentDiscountVoucher");
    private final String inputVoucherType;

    VoucherType(String inputVoucherType) {
        this.inputVoucherType = inputVoucherType;
    }

    public static VoucherType checkVoucherType(String voucherType) {
        return Arrays.stream(values())
                .filter(type -> type.inputVoucherType.equalsIgnoreCase(voucherType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 바우처 타입입니다."));
    }
}

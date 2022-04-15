package me.programmers.springboot.basic.springbootbasic.voucher;

import java.util.Arrays;

public enum VoucherType {
    FIXED, PERCENT;

    public static VoucherType getVoucherStatus(String voucherType) {
        return Arrays.stream(VoucherType.values())
                .filter(e -> e.name().equals(voucherType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 바우처 타입 입력"));
    }
}

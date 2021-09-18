package org.prgrms.devcourse.voucher;

import java.util.Arrays;

public enum VoucherType {
    FIXED_AMOUNT_DISCOUNT_VOUCHER("1"),
    PERCENT_DISCOUNT_VOUCHER("2");

    private String voucherTypeNumber;

    VoucherType(String voucherTypeNumber) {
        this.voucherTypeNumber = voucherTypeNumber;
    }

    public static VoucherType findByTypeNumber(String userInput) {
        return Arrays.stream(values())
                .filter(m -> m.voucherTypeNumber.equals(userInput))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 타입 입니다."));
    }

    public static VoucherType findByString(String voucherType) {
        return Arrays.stream(values())
                .filter(m -> m.toString().equals(voucherType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 타입 입니다."));
    }
}

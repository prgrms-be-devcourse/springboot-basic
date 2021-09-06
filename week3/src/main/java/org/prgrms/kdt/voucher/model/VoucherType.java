package org.prgrms.kdt.voucher.model;

import java.util.Arrays;

public enum VoucherType {
    FIXED_AMOUNT_VOUCHER ("FixedAmountVoucher"),
    PERCENT_DISCOUNT_VOUCHER ("PercentDiscountVoucher");

    private final String type;

    VoucherType(String type) {
        this.type = type;
    }

    public static VoucherType getVoucherType(String type) {
        return Arrays.stream(values())
                .filter(voucherType -> voucherType.type.equals(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("알 수 없는 값 입력됨: " + type));
    }
}

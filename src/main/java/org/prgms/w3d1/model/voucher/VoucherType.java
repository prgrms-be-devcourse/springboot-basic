package org.prgms.w3d1.model.voucher;

import java.util.Arrays;

public enum VoucherType {
    FIXED_AMOUNT_VOUCHER("FixedAmountVoucher"),
    PERCENT_DISCOUNT_VOUCHER("PercentDiscountVoucher");

    private final String type;

    VoucherType(String type){
        this.type = type;
    }

    public String getValue() {
        return type;
    }

    public static VoucherType getType(String str) {
        return Arrays.stream(values())
            .filter(v -> v.type.equals(str))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("unknown value: " + str));
    }
}

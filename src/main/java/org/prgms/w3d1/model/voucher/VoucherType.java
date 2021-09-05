package org.prgms.w3d1.model.voucher;

import java.util.Arrays;

public enum VoucherType {
    FIXED_AMOUNT_VOUCHER("FixedAmountVoucher"),
    PERCENT_DISCOUNT_VOUCHER("PercentDiscountVoucher");

    private final String command;

    VoucherType(String command){
        this.command = command;
    }

    public static VoucherType getCommand(String str) {
        return Arrays.stream(values())
            .filter(v -> v.command.equals(str))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("unknown value: " + str));
    }
}

package org.prgrms.springbootbasic.type;


import java.util.Arrays;

public enum VoucherType {
    FIXED, PERCENT;

    public static boolean isValidType(String voucherType) {
        return Arrays.stream(VoucherType.values())
                .map(String::valueOf)
                .anyMatch(v -> v.equals(voucherType));
    }
}

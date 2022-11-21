package org.prgms.springbootbasic.domain.voucher;

import java.util.Arrays;

public enum VoucherType {
    FIXED("fixed"),
    PERCENT("percent");

    private final String voucherType;

    VoucherType(String voucherType) {
        this.voucherType = voucherType;
    }


    public static VoucherType findVoucherType(String input) {
        return Arrays.stream(VoucherType.values())
                .filter(e -> e.voucherType.equals(input.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("wrong voucher type entered :\t {" + input+"}"));
    }
}

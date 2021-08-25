package org.programmers.kdt.voucher;

import java.text.MessageFormat;
import java.util.Arrays;

public enum VoucherType {
    FIXED("fixed"), PERCENT("percent");

    private final String voucherType;

    VoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public static VoucherType of(String type) {
        return Arrays.stream(values())
                .filter(iter -> iter.getVoucherType().equals(type.toLowerCase()))
                .findAny()
                .orElseThrow(
                        () -> new RuntimeException(MessageFormat.format("Invalid voucher Type : {0}", type))
                );
    }

    public String getVoucherType() {
        return voucherType;
    }
}

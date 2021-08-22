package com.prgrm.kdt.voucher.domain;

import java.util.Arrays;

public enum VoucherType {

    FIXED("FIXED"),
    PERCENT("PERCENT");

    private final String type;

    VoucherType(final String type) {
        this.type = type;
    }

    public static VoucherType findByVoucherType(String inputType) {
        return Arrays.stream(VoucherType.values())
                .filter(b -> b.type.equals(inputType))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Voucher type does not match"));
    }
}

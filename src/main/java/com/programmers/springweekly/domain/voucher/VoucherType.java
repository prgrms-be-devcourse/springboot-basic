package com.programmers.springweekly.domain.voucher;

import java.util.Arrays;

public enum VoucherType {
    FIXED("fixed"),
    PERCENT("percent");

    private final String type;

    VoucherType(String type) {
        this.type = type;
    }

    public static VoucherType findVoucherMenu(String type){
        return Arrays.stream(VoucherType.values())
                .filter(item -> item.type.equals(type))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("The type you are looking for is not found."));
    }

    public String getVoucherTypeString(){
        return type;
    }
}
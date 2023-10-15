package com.prgrms.springbasic.domain.voucher.entity;

import java.util.Arrays;
import java.util.List;

public enum DiscountType {
    FIXED,
    PERCENT,
    ;

    public static DiscountType find(String type) {
        return DiscountType.valueOf(type.toUpperCase());
    }

    public static List<String> allowedDiscountTypes() {
        return Arrays.stream(DiscountType.values())
                .map(Enum::name)
                .map(String::toLowerCase)
                .toList();
    }
}

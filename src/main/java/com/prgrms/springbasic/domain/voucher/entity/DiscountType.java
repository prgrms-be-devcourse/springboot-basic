package com.prgrms.springbasic.domain.voucher.entity;

import com.prgrms.springbasic.common.exception.InvalidValueException;

import java.util.Arrays;
import java.util.List;

public enum DiscountType {
    FIXED,
    PERCENT,
    ;

    public static DiscountType find(String type) {
        String upperCase = type.toUpperCase();
        for (DiscountType discountType : values()) {
            if (discountType.name().equals(upperCase)) {
                return discountType;
            }
        }
        throw new InvalidValueException("Invalid DiscountType. Inserted type : " + type);
    }

    public static List<String> allowedDiscountTypes() {
        return Arrays.stream(DiscountType.values())
                .map(Enum::name)
                .map(String::toLowerCase)
                .toList();
    }
}

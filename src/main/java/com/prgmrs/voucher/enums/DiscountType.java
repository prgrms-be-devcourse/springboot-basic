package com.prgmrs.voucher.enums;

import com.prgmrs.voucher.exception.WrongRangeFormatException;

public enum DiscountType {
    FIXED_AMOUNT_DISCOUNT("fixed", 1),
    PERCENT_DISCOUNT("percent", 2);

    private final String stringValue;
    private final int value;

    DiscountType(String stringValue, int value) {
        this.stringValue = stringValue;
        this.value = value;
    }

    public static DiscountType fromString(String value) {
        for (DiscountType enumValue : DiscountType.values()) {
            if (enumValue.stringValue.equalsIgnoreCase(value)) {
                return enumValue;
            }
        }
        throw new WrongRangeFormatException("no matching discount type from string");
    }

    public static DiscountType fromShort(short value) {
        for (DiscountType enumValue : DiscountType.values()) {
            if (enumValue.value == value) {
                return enumValue;
            }
        }
        throw new WrongRangeFormatException("no matching discount type from short");
    }

    public static int fromEnumValueStringToValue(String value) {
        for (DiscountType enumValue : DiscountType.values()) {
            if (enumValue.name().equalsIgnoreCase(value)) {
                return enumValue.value;
            }
        }
        throw new WrongRangeFormatException("no matching discount type from enum");
    }

    public int getValue() {
        return value;
    }
}
